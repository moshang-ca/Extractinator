package tech.alexnijjar.extractinator.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.resources.ResourceLocation;
import tech.alexnijjar.extractinator.client.ExtractinatorClient;

import java.util.ArrayList;
import java.util.List;

public class ExtractinatorClientFabric implements ClientModInitializer {
    @SuppressWarnings("unchecked")
    @Override
    public void onInitializeClient() {
        ExtractinatorClient.onRegisterItemRenderers((item, renderer) -> BuiltinItemRendererRegistry.INSTANCE.register(item.asItem(), renderer::renderByItem));

        List<ResourceLocation> extraModels = new ArrayList<>();
        ExtractinatorClient.onRegisterModels(mrl -> {
            ResourceLocation rl = mrl.id();
            extraModels.add(rl);
        });
        ModelLoadingPlugin.register(ctx -> ctx.addModels(extraModels));

        ExtractinatorClient.onRegisterBlockRenderers((type, factory) -> BlockEntityRenderers.register(type.get(), factory));
    }
}
