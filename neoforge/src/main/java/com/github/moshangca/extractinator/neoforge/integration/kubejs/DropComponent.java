package com.github.moshangca.extractinator.neoforge.integration.kubejs;

import com.mojang.serialization.Codec;
import dev.latvian.mods.kubejs.recipe.RecipeScriptContext;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.NativeObject;
import dev.latvian.mods.rhino.ScriptableObject;
import dev.latvian.mods.rhino.type.TypeInfo;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import tech.alexnijjar.extractinator.Extractinator;
import tech.alexnijjar.extractinator.common.recipe.ExtractinatorRecipe;

public class DropComponent implements RecipeComponent<ExtractinatorRecipe.Drop> {
    public static final RecipeComponentType<ExtractinatorRecipe.Drop> TYPE =
        RecipeComponentType.unit(ResourceLocation.fromNamespaceAndPath(Extractinator.MOD_ID, "drop"), DropComponent::new);

    private final RecipeComponentType<ExtractinatorRecipe.Drop> type;

    public DropComponent(RecipeComponentType<ExtractinatorRecipe.Drop> type) {
        this.type = type;
    }

    @Override
    public RecipeComponentType<?> type() {
        return type;
    }

    @Override
    public Codec<ExtractinatorRecipe.Drop> codec() {
        return ExtractinatorRecipe.Drop.CODEC;
    }

    @Override
    public ExtractinatorRecipe.Drop wrap(RecipeScriptContext cx, Object from) {
        if (from instanceof ExtractinatorRecipe.Drop drop) {
            return drop;
        }
        if (!(from instanceof NativeObject obj)) {
            throw new IllegalArgumentException("Expected a Drop object, got: " + from);
        }

        Context rhino = cx.cx();
        String dropStr = ScriptableObject.getProperty(obj, "drop", rhino) instanceof String s ? s : null;
        if (dropStr == null) throw new IllegalArgumentException("Drop object must have a 'drop' field");

        double chance = toFloat(ScriptableObject.getProperty(obj, "chance", rhino), 1.f);
        int min = toInt(ScriptableObject.getProperty(obj, "min", rhino), 1);
        int max = toInt(ScriptableObject.getProperty(obj, "max", rhino), 1);

        HolderSet<Item> holderSet;
        if (dropStr.startsWith("#")) {
            TagKey<Item> tagKey = TagKey.create(Registries.ITEM, ResourceLocation.parse(dropStr.substring(1)));
            holderSet = BuiltInRegistries.ITEM.getTag(tagKey).orElseThrow(() -> new IllegalArgumentException("Unknown tag: " + tagKey));
        } else  {
            Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(dropStr));
            if (item == Items.AIR) throw new IllegalArgumentException("Unknown item: " + dropStr);
            holderSet = HolderSet.direct(BuiltInRegistries.ITEM.wrapAsHolder(item));
        }
        return new ExtractinatorRecipe.Drop(holderSet, chance, min, max);
    }

    @Override
    public TypeInfo typeInfo() {
        return TypeInfo.of(ExtractinatorRecipe.Drop.class);
    }

    private static float toFloat(Object value, float fallback) {
        if (value instanceof Number n) return n.floatValue();
        return fallback;
    }

    private static int toInt(Object value, int fallback) {
        if (value instanceof Number n) return n.intValue();
        return fallback;
    }
}
