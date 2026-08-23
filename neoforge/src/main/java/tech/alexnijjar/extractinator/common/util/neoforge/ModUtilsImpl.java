package tech.alexnijjar.extractinator.common.util.neoforge;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

public class ModUtilsImpl {
    public static boolean isExtractableContainer(@NotNull Level level, BlockPos pos) {
        return level.getCapability(Capabilities.ItemHandler.BLOCK, pos, null) != null;
    }

    public static void outputToContainer(@NotNull Level level, BlockPos pos, ItemStack output) {
        IItemHandler handler = level.getCapability(Capabilities.ItemHandler.BLOCK, pos, null);
        if (handler == null) return;
        for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack stack = handler.getStackInSlot(i);
            if (stack.isEmpty() || ItemStack.isSameItem(output, stack)) {
                ItemStack remainder = handler.insertItem(i, output.copy(), false);
                output.setCount(remainder.getCount());
                if (output.isEmpty()) return;
            }
        }
    }
}
