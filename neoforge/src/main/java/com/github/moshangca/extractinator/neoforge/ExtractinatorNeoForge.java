package com.github.moshangca.extractinator.neoforge;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import tech.alexnijjar.extractinator.Extractinator;
import tech.alexnijjar.extractinator.client.ExtractinatorClient;

@Mod(Extractinator.MOD_ID)
public class ExtractinatorNeoForge {
    public ExtractinatorNeoForge(IEventBus bus, ModContainer mod, Dist dist) {
        Extractinator.init();

        if (dist == Dist.CLIENT)
            ExtractinatorClientNeoForge.init(mod);

        bus.addListener(ExtractinatorNeoForge::onClientSetup);
    }

    public static void onClientSetup(FMLClientSetupEvent event) {
        ExtractinatorClient.initializeClient();
        ExtractinatorClientNeoForge.postInit();
    }
}
