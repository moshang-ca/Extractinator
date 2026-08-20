package tech.alexnijjar.extractinator.common.registry;

import com.mojang.serialization.Codec;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import tech.alexnijjar.extractinator.Extractinator;

public class ModDataComponents {
    public static final ResourcefulRegistry<DataComponentType<?>> COMPONENT_TYPES =
        ResourcefulRegistries.create(BuiltInRegistries.DATA_COMPONENT_TYPE, Extractinator.MOD_ID);

    public static final RegistryEntry<DataComponentType<Integer>> REMAIN_USAGES =
        COMPONENT_TYPES.register("remain_usages", () -> DataComponentType.<Integer>builder()
            .persistent(Codec.INT)
            .networkSynchronized(ByteBufCodecs.INT)
            .build()
        );
}
