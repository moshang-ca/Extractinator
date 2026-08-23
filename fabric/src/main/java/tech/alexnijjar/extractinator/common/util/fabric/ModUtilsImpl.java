package tech.alexnijjar.extractinator.common.util.fabric;

import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ModUtilsImpl {
    public static boolean isExtractableContainer(@NotNull Level level, BlockPos pos) {
        return ItemStorage.SIDED.find(level, pos, null) != null;
    }

    public static void outputToContainer(@NotNull Level level, BlockPos pos, ItemStack output) {
        Storage<ItemVariant> storage = ItemStorage.SIDED.find(level, pos, null);
        if (storage == null) return;
        try (Transaction transaction = Transaction.openOuter()) {
            long inserted = storage.insert(ItemVariant.of(output), output.getCount(), transaction);
            if (inserted > 0) {
                transaction.commit();
                output.shrink((int) inserted);
            }
        }
    }
}
