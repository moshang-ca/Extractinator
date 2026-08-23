package tech.alexnijjar.extractinator.common.util;

import com.teamresourceful.resourcefullib.common.exceptions.NotImplementedException;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import tech.alexnijjar.extractinator.common.config.ExtractinatorConfig;
import tech.alexnijjar.extractinator.common.recipe.ExtractinatorRecipe;

import java.util.ArrayList;
import java.util.List;

public class ModUtils {
    public static final List<Direction> DIRECTIONS = List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);

    public static List<ItemStack> extractItem(ExtractinatorRecipe recipe, Level level) {
        if (recipe == null) return List.of();

        RandomSource random = level.getRandom();
        List<ItemStack> drops = new ArrayList<>();
        for (ExtractinatorRecipe.Drop drop : recipe.outputs()) {
            int dropCount = (int) ((random.nextInt(drop.maxDropCount() - drop.minDropCount() + 1) + drop.minDropCount()) * ExtractinatorConfig.lootMultiplier);
            double randomPercent = random.nextDouble();
            if (randomPercent <= drop.dropChance()) {
                drop.drops().getRandomElement(random).ifPresent(d -> drops.add(new ItemStack(d.value(), dropCount)));
            }
        }

        return drops;
    }

    public static boolean isValidInput(ExtractinatorRecipe recipe, ItemStack stack) {
        return recipe != null && recipe.input().test(stack);
    }

    @ExpectPlatform
    public static boolean isExtractableContainer(@NotNull Level level, BlockPos pos) {
        return true;
    }

    @ExpectPlatform
    public static void outputToContainer(@NotNull Level level, BlockPos pos, ItemStack output) {
        throw new NotImplementedException();
    }
}
