package com.github.moshangca.extractinator.neoforge;

import com.github.moshangca.extractinator.config.NeoForgeMenuConfig;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.event.ModelEvent;
import tech.alexnijjar.extractinator.client.ExtractinatorClient;

import java.util.HashMap;
import java.util.Map;

public class ExtractinatorClientNeoForge {
    private static final Map<Item, BlockEntityWithoutLevelRenderer> ITEM_RENDERERS = new HashMap<>();
    private static boolean hasInitializedRenderers = false;

    public static void init(ModContainer mod) {
        IEventBus bus = mod.getEventBus();
        if (bus == null) return;
        bus.addListener(ExtractinatorClientNeoForge::modelLoading);
        NeoForgeMenuConfig.register(mod);
    }

    public static void modelLoading(ModelEvent.RegisterAdditional event) {
        ExtractinatorClient.onRegisterModels(event::register);
    }

    @SuppressWarnings("unchecked")
    public static void postInit() {
        ExtractinatorClient.onRegisterItemRenderers((item, renderer) -> ITEM_RENDERERS.put(item.asItem(), renderer));
        ExtractinatorClient.onRegisterBlockRenderers((type, factory) -> BlockEntityRenderers.register(type.get(), factory));
        hasInitializedRenderers = true;
    }

    public static BlockEntityWithoutLevelRenderer getItemRenderer(ItemLike item) {
        return ITEM_RENDERERS.get(item.asItem());
    }

    public static boolean hasInitializedRenderers() {
        return hasInitializedRenderers;
    }
}
