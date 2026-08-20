package com.github.moshangca.extractinator.common.config.neoforge;

import com.teamresourceful.resourcefulconfig.api.types.ResourcefulConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import tech.alexnijjar.extractinator.Extractinator;
import tech.alexnijjar.extractinator.common.config.ExtractinatorConfig;

public class NeoForgeMenuConfig {
    public static void register(ModContainer mod) {
        mod.registerExtensionPoint(IConfigScreenFactory.class,
            (mc, parent) -> {
                ResourcefulConfig config = Extractinator.CONFIGURATOR.getConfig(ExtractinatorConfig.class);
                assert config != null;

                return new ConfigurationScreen(mc, parent);
            }
        );
    }
}
