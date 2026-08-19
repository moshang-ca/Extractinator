package tech.alexnijjar.extractinator.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import tech.alexnijjar.extractinator.Extractinator;
import tech.alexnijjar.extractinator.common.block.ExtractinatorBlock;

public class ModBlocks {
    public static final ResourcefulRegistry<Block> BLOCKS = ResourcefulRegistries.create(BuiltInRegistries.BLOCK, Extractinator.MOD_ID);

    public static final RegistryEntry<Block> EXTRACTINATOR = BLOCKS.register("extractinator", () -> new ExtractinatorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryEntry<Block> SILT = BLOCKS.register("silt", () -> new ColoredFallingBlock(new ColorRGBA(0x696969), BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).strength(1.8f)));
    public static final RegistryEntry<Block> SLUSH = BLOCKS.register("slush", () -> new ColoredFallingBlock(new ColorRGBA(0x7AA8F2), BlockBehaviour.Properties.ofFullCopy(Blocks.SNOW_BLOCK).strength(1.8f)));
}
