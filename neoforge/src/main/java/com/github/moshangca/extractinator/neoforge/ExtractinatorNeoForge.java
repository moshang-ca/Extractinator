package com.github.moshangca.extractinator.neoforge;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import tech.alexnijjar.extractinator.Extractinator;
import tech.alexnijjar.extractinator.client.ExtractinatorClient;
import tech.alexnijjar.extractinator.common.registry.ModItems;

@Mod(Extractinator.MOD_ID)
public class ExtractinatorNeoForge {
    public ExtractinatorNeoForge(IEventBus bus, ModContainer mod, Dist dist) {
        Extractinator.init();

        if (dist == Dist.CLIENT)
            ExtractinatorClientNeoForge.init(mod);

        bus.addListener(ExtractinatorNeoForge::onRegister);
        bus.addListener(ExtractinatorNeoForge::onClientSetup);
    }

    public static void onRegister(RegisterEvent event) {
        if (event.getRegistryKey().equals(Registries.CREATIVE_MODE_TAB)) {
            Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ModItems.TAB.id, ModItems.TAB.build());
        }
    }

    public static void onClientSetup(FMLClientSetupEvent event) {
        ExtractinatorClient.initializeClient();
        ExtractinatorClientNeoForge.postInit();
    }
}
