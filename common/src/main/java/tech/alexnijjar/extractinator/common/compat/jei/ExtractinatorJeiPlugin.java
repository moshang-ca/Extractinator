package tech.alexnijjar.extractinator.common.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IModIngredientRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import tech.alexnijjar.extractinator.Extractinator;
import tech.alexnijjar.extractinator.common.registry.ModItems;
import tech.alexnijjar.extractinator.common.registry.ModRecipeTypes;

@JeiPlugin
public class ExtractinatorJeiPlugin implements IModPlugin {
    @Override
    @NotNull
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(Extractinator.MOD_ID, "jei");
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registration) {
        IModPlugin.super.registerIngredients(registration);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new ExtractinatorCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        Level level = Minecraft.getInstance().level;
        assert level != null;
        registration.addRecipes(ExtractinatorCategory.RECIPE, level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.EXTRACTINATOR_RECIPE.get()).stream().map(RecipeHolder::value).toList());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ModItems.EXTRACTINATOR.get().getDefaultInstance(), ExtractinatorCategory.RECIPE);
    }
}
