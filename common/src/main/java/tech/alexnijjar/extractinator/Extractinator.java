package tech.alexnijjar.extractinator;

import com.teamresourceful.resourcefulconfig.api.loader.Configurator;
import tech.alexnijjar.extractinator.common.config.ExtractinatorConfig;
import tech.alexnijjar.extractinator.common.registry.*;

public class Extractinator {
    public static final String MOD_ID = "extractinator";
    public static final Configurator CONFIGURATOR = new Configurator(MOD_ID);

    public static void init() {
        CONFIGURATOR.register(ExtractinatorConfig.class);

        ModBlocks.BLOCKS.init();
        ModBlockEntityTypes.BLOCK_ENTITY_TYPES.init();
        ModItems.ITEMS.init();
        ModRecipeTypes.RECIPE_TYPES.init();
        ModRecipeSerializers.RECIPE_SERIALIZERS.init();
        ModFeatures.FEATURES.init();
        ModDataComponents.COMPONENT_TYPES.init();
    }
}