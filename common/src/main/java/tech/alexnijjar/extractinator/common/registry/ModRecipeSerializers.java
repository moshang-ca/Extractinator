package tech.alexnijjar.extractinator.common.registry;

import com.mojang.serialization.MapCodec;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeSerializer;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import tech.alexnijjar.extractinator.Extractinator;
import tech.alexnijjar.extractinator.common.recipe.ExtractinatorRecipe;

public class ModRecipeSerializers {
    public static final ResourcefulRegistry<RecipeSerializer<?>> RECIPE_SERIALIZERS = ResourcefulRegistries.create(BuiltInRegistries.RECIPE_SERIALIZER, Extractinator.MOD_ID);

    public static final RegistryEntry<RecipeSerializer<ExtractinatorRecipe>> EXTRACTINATOR_SERIALIZER = RECIPE_SERIALIZERS.register("extractinating", () -> new CodecRecipeSerializer<>(ModRecipeTypes.EXTRACTINATOR_RECIPE.get(), ExtractinatorRecipe.CODEC, ExtractinatorRecipe.STREAM_CODEC));
}
