package tech.alexnijjar.extractinator.common.registry;

import com.teamresourceful.resourcefullib.common.item.tabs.ResourcefulCreativeModeTab;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import tech.alexnijjar.extractinator.Extractinator;
import tech.alexnijjar.extractinator.common.block.ExtractinatorBlockItem;

@SuppressWarnings("unused")
public class ModItems {
    public static final ResourcefulRegistry<Item> ITEMS = ResourcefulRegistries.create(BuiltInRegistries.ITEM, Extractinator.MOD_ID);
    public static final ResourcefulCreativeModeTab TAB = new ResourcefulCreativeModeTab(ResourceLocation.fromNamespaceAndPath(Extractinator.MOD_ID, "main"))
        .setItemIcon(ModBlocks.EXTRACTINATOR)
        .addRegistry(ITEMS);

    public static final RegistryEntry<Item> EXTRACTINATOR = ITEMS.register("extractinator", () -> new ExtractinatorBlockItem(ModBlocks.EXTRACTINATOR.get(), new Item.Properties()));
    public static final RegistryEntry<Item> SILT = ITEMS.register("silt", () -> new BlockItem(ModBlocks.SILT.get(), new Item.Properties()));
    public static final RegistryEntry<Item> SLUSH = ITEMS.register("slush", () -> new BlockItem(ModBlocks.SLUSH.get(), new Item.Properties()));
}
