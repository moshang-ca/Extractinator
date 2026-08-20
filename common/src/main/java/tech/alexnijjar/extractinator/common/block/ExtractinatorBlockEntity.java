package tech.alexnijjar.extractinator.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.alexnijjar.extractinator.common.config.ExtractinatorConfig;
import tech.alexnijjar.extractinator.common.recipe.ExtractinatorRecipe;
import tech.alexnijjar.extractinator.common.registry.ModBlockEntityTypes;
import tech.alexnijjar.extractinator.common.registry.ModRecipeTypes;
import tech.alexnijjar.extractinator.common.util.ModUtils;

import java.util.List;

public class ExtractinatorBlockEntity extends BlockEntity implements ExtractinatorContainer {
    private final NonNullList<ItemStack> inventory;
    protected int remainingUsages;
    @Nullable
    private ExtractinatorRecipe recipe;
    private ItemStack prevInput = ItemStack.EMPTY;

    public ExtractinatorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityTypes.EXTRACTINATOR.get(), blockPos, blockState);
        inventory = NonNullList.withSize(33, ItemStack.EMPTY);
    }

    public void serverTick() {
        if (level != null && level.getGameTime() % ExtractinatorConfig.extractTicks == 0) {
            extractinate();
        }
    }

    protected void extractinate() {
        if (this.level != null) {
            dispenseItems();
            placeBlockAbove();
            extractBlockAbove();
            extractItems();
            setChanged();
        }
    }

    protected void placeBlockAbove() {
        if (level == null) return;

        BlockState above = level.getBlockState(this.getBlockPos().above());
        ItemStack input = inventory.getFirst();
        BlockState toPlaceState = Block.byItem(input.getItem()).defaultBlockState();
        if (toPlaceState.isAir()) return;

        if (above.isAir() || Blocks.WATER.equals(above.getBlock())) {
            level.setBlock(this.getBlockPos().above(), toPlaceState, Block.UPDATE_NONE);
        } else {
            if (!ExtractinatorConfig.silent) {
                level.playSound(null, this.getBlockPos(), toPlaceState.getSoundType().getBreakSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
            }
            List<ItemStack> outputs = ModUtils.extractItem(this.recipe, level);
            if (!outputs.isEmpty()) {
                outputs.forEach(this::addItem);
            }
        }
        getItem(0).shrink(1);
    }

    protected void extractBlockAbove() {
        if (level == null) return;

        BlockState above = level.getBlockState(this.getBlockPos().above());
        if (above.isAir()) return;
        extractStack(above.getBlock().asItem().getDefaultInstance());
    }

    protected void extractItems() {
        var stack = getItem(0);
        if (stack.getItem() instanceof BlockItem) return;
        if (extractStack(stack)) stack.shrink(1);
    }

    protected boolean extractStack(ItemStack stack) {
        if (level == null || !isValidInput(stack)) return false;

        if (ExtractinatorConfig.silent) {
            level.removeBlock(this.getBlockPos().above(), false);
        } else {
            level.destroyBlock(this.getBlockPos().above(), false);
        }
        List<ItemStack> outputs = ModUtils.extractItem(this.recipe, level);
        damage();
        if (!outputs.isEmpty()) outputs.forEach(this::addItem);
        return true;
    }

    protected void dispenseItems() {
        if (level == null) return;

        for (int i = 1; i < getInventory().size(); i++) {
            ItemStack stack = getItem(i);
            if (stack.isEmpty()) continue;
            if (!level.getBlockState(getBlockPos().above()).isAir()) continue;
            BlockPos pos = this.getBlockPos();
            ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5f, pos.getY() + 2.0f, pos.getZ() + 0.5f, stack.copy());
            itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().scale(1.5f));
            stack.setCount(0);
            level.addFreshEntity(itemEntity);
            break;
        }
    }

    public void damage() {
        if (this.level != null) {
            if (ExtractinatorConfig.extractinatorDurability > 0) {
                this.remainingUsages--;
                if (this.remainingUsages == 0) {
                    level.playSound(null, this.getBlockPos(), SoundEvents.ANVIL_DESTROY, SoundSource.BLOCKS, 1, 1);
                    level.destroyBlock(this.getBlockPos(), false);
                }
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, this.inventory, registries);
        tag.putInt("RemainingUsages", remainingUsages);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        ContainerHelper.loadAllItems(tag, this.inventory, registries);
        this.remainingUsages = tag.getInt("RemainingUsages");
    }

    @Override
    @NotNull
    public NonNullList<ItemStack> getInventory() {
        return this.inventory;
    }

    @Override
    public boolean isValidInput(ItemStack stack) {
        if (level == null || stack.isEmpty()) return false;
        if (!ItemStack.isSameItem(this.prevInput, stack)) {
            RecipeHolder<ExtractinatorRecipe> holderResult = level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.EXTRACTINATOR_RECIPE.get()).stream().filter(holder -> holder.value().matches(stack, level)).findFirst().orElse(null);
            this.recipe = holderResult != null ? holderResult.value() : null;
        }
        this.prevInput = stack;
        return ModUtils.isValidInput(this.recipe, stack);
    }
}