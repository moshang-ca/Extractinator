package tech.alexnijjar.extractinator.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.tags.HolderSetCodec;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipe;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeSerializer;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import tech.alexnijjar.extractinator.common.registry.ModRecipeSerializers;
import tech.alexnijjar.extractinator.common.registry.ModRecipeTypes;

import java.util.List;

public record ExtractinatorRecipe(Ingredient input, List<Drop> outputs) implements CodecRecipe<SingleRecipeInput> {

    public static MapCodec<ExtractinatorRecipe> CODEC =
        RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.fieldOf("input").forGetter(ExtractinatorRecipe::input),
            Drop.CODEC.listOf().fieldOf("drops").forGetter(ExtractinatorRecipe::outputs)
        ).apply(instance, ExtractinatorRecipe::new));

    public static StreamCodec<RegistryFriendlyByteBuf, ExtractinatorRecipe> STREAM_CODEC =
        StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC, ExtractinatorRecipe::input,
            Drop.STREAM_CODEC.apply(ByteBufCodecs.list()), ExtractinatorRecipe::outputs,
            ExtractinatorRecipe::new
        );

    @Override
    public boolean matches(SingleRecipeInput input, Level level) {
        return this.input.test(input.item());
    }

    public boolean matches(ItemStack stack, Level level) { return matches(new SingleRecipeInput(stack), level); }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.EXTRACTINATOR_SERIALIZER.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public CodecRecipeSerializer<? extends CodecRecipe<SingleRecipeInput>> serializer() {
        return (CodecRecipeSerializer<? extends CodecRecipe<SingleRecipeInput>>) getSerializer();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.EXTRACTINATOR_RECIPE.get();
    }

    public List<Ingredient> getOutputs() {
        return this.outputs()
            .stream()
            .map(d -> d.drops.stream()
                .map(Holder::value)
                .map(i -> new ItemStack(i, d.maxDropCount()))
                .map(Ingredient::of).toList())
            .flatMap(List::stream).toList();
    }

    public record Drop(HolderSet<Item> drops, double dropChance, int minDropCount, int maxDropCount) {
        public static final Codec<Drop> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            HolderSetCodec.of(BuiltInRegistries.ITEM).fieldOf("drop").forGetter(Drop::drops),
            Codec.DOUBLE.fieldOf("drop_chance").orElse(1.0).forGetter(Drop::dropChance),
            Codec.INT.fieldOf("min_drop_count").orElse(1).forGetter(Drop::minDropCount),
            Codec.INT.fieldOf("max_drop_count").orElse(1).forGetter(Drop::maxDropCount)
        ).apply(instance, Drop::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, Drop> STREAM_CODEC =
            StreamCodec.composite(
                ByteBufCodecs.holderSet(BuiltInRegistries.ITEM.key()), Drop::drops,
                ByteBufCodecs.DOUBLE, Drop::dropChance,
                ByteBufCodecs.INT, Drop::minDropCount,
                ByteBufCodecs.INT, Drop::maxDropCount,
                Drop::new
            );
    }
}