package tech.alexnijjar.extractinator.common.compat.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import tech.alexnijjar.extractinator.Extractinator;
import tech.alexnijjar.extractinator.common.recipe.ExtractinatorRecipe;
import tech.alexnijjar.extractinator.common.registry.ModItems;

import java.util.List;

public class ExtractinatorCategory extends BaseCategory<ExtractinatorRecipe> {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Extractinator.MOD_ID, "extractinating");
    public static final RecipeType<ExtractinatorRecipe> RECIPE = new RecipeType<>(ID, ExtractinatorRecipe.class);
    private final IDrawable slot;

    public ExtractinatorCategory(IGuiHelper guiHelper) {
        super(guiHelper,
            RECIPE,
            Component.translatable(ModItems.EXTRACTINATOR.get().getDescriptionId()),
            guiHelper.createBlankDrawable(144, 144),
            guiHelper.createDrawableItemStack(ModItems.EXTRACTINATOR.get().getDefaultInstance())
        );
        slot = guiHelper.getSlotDrawable();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ExtractinatorRecipe recipe, IFocusGroup focuses) {
        builder.addInvisibleIngredients(RecipeIngredientRole.CATALYST).addIngredients(Ingredient.of(ModItems.EXTRACTINATOR.get()));
        builder.addSlot(RecipeIngredientRole.INPUT, 64, 1).addIngredients(recipe.input());
        List<ExtractinatorRecipe.Drop> drops = recipe.outputs();

        int i = 0, j = 0;
        int dropIndex = 0;
        while (dropIndex < drops.size()) {
            ExtractinatorRecipe.Drop d = drops.get(dropIndex);
            List<Ingredient> dIng = d.drops().stream().map(Holder::value)
                .map(item -> new ItemStack(item, 1))
                .map(Ingredient::of).toList();
            for (var ing : dIng) {
                builder.addSlot(RecipeIngredientRole.OUTPUT, i * 18 + 1, j * 18 + 21)
                    .addIngredients(ing)
                    .addRichTooltipCallback(((recipeSlotView, tooltip) -> {
                        tooltip.add(Component.translatable("text.extractinator.drop_chance", d.dropChance()));
                        tooltip.add(Component.translatable("text.extractinator.max_drop_count", d.maxDropCount()));
                        tooltip.add(Component.translatable("text.extractinator.min_drop_count", d.minDropCount()));
                    }));
                if ((++i) >= 8) {
                    i = 0; j++;
                }
            }
            dropIndex++;
        }
    }

    @Override
    public void draw(ExtractinatorRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        slot.draw(guiGraphics, 63, 0);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                slot.draw(guiGraphics, i * 18, j * 18 + 20);
            }
        }
    }
}
