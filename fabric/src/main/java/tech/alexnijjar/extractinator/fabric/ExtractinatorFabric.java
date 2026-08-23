package tech.alexnijjar.extractinator.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import tech.alexnijjar.extractinator.Extractinator;
import tech.alexnijjar.extractinator.common.config.ExtractinatorConfig;
import tech.alexnijjar.extractinator.common.registry.ModBlockEntityTypes;
import tech.alexnijjar.extractinator.common.registry.ModItems;
import tech.alexnijjar.extractinator.fabric.storage.ExtractinatorItemStorage;

public class ExtractinatorFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Extractinator.init();
        addExtraRegistries();
        addBiomeModifications();
    }

    public static void addExtraRegistries() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ModItems.TAB.id, ModItems.TAB.build());

        ItemStorage.SIDED.registerForBlockEntity(
            (be, direction) -> new ExtractinatorItemStorage(be),
            ModBlockEntityTypes.EXTRACTINATOR.get()
        );
    }

    public static void addBiomeModifications() {
        if (ExtractinatorConfig.worldgen) {
            BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Decoration.UNDERGROUND_ORES,
                ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(Extractinator.MOD_ID, "silt")));
            BiomeModifications.addFeature(BiomeSelectors.tag(ConventionalBiomeTags.IS_ICY), GenerationStep.Decoration.UNDERGROUND_ORES,
                ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(Extractinator.MOD_ID, "slush")));
        }
    }
}