package com.github.moshangca.extractinator.mixin.neoforge;

import com.github.moshangca.extractinator.neoforge.ExtractinatorClientNeoForge;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import tech.alexnijjar.extractinator.common.block.ExtractinatorBlockItem;

import java.util.function.Consumer;

@Mixin(ExtractinatorBlockItem.class)
public abstract class ExtractinatorBlockItemMixin extends Item {

    public ExtractinatorBlockItemMixin(Properties settings) {
        super(settings);
    }

    @Override
    @SuppressWarnings("removal")
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        Item item = this;

        consumer.accept(new IClientItemExtensions() {
            private BlockEntityWithoutLevelRenderer renderer = null;
            private boolean hasCheckedSinceInit = false;

            @Override
            @NotNull
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (ExtractinatorClientNeoForge.hasInitializedRenderers() && !hasCheckedSinceInit) {
                    renderer = ExtractinatorClientNeoForge.getItemRenderer(item);
                    hasCheckedSinceInit = true;
                }
                return renderer == null ? IClientItemExtensions.super.getCustomRenderer() : renderer;
            }
        });
    }
}
