package com.github.moshangca.extractinator.neoforge.capability;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import tech.alexnijjar.extractinator.common.block.ExtractinatorBlockEntity;

@MethodsReturnNonnullByDefault
public class ExtractinatorItemHandler implements IItemHandler {
    private final ExtractinatorBlockEntity be;

    public ExtractinatorItemHandler(ExtractinatorBlockEntity be) {
        this.be = be;
    }


    @Override
    public int getSlots() {
        return be.getContainerSize();
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return be.getItem(i);
    }

    @Override
    public ItemStack insertItem(int i, @NotNull ItemStack arg, boolean bl) {
        if (i != 0) return ItemStack.EMPTY;
        return be.addItemToInput(arg, bl);
    }

    @Override
    public ItemStack extractItem(int i, int j, boolean bl) {
        return be.extractItem(i, j, bl);
    }

    @Override
    public int getSlotLimit(int i) {
        return 64;
    }

    @Override
    public boolean isItemValid(int i, @NotNull ItemStack arg) {
        return true;
    }
}
