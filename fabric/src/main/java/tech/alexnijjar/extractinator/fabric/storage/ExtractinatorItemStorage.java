package tech.alexnijjar.extractinator.fabric.storage;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import tech.alexnijjar.extractinator.common.block.ExtractinatorBlockEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExtractinatorItemStorage implements Storage<ItemVariant> {
    private final ExtractinatorBlockEntity be;

    public ExtractinatorItemStorage(ExtractinatorBlockEntity be) { this.be = be; }

    @Override
    public long extract(ItemVariant resource, long maxAmount, TransactionContext transaction) {
        ItemStack stack = resource.toStack((int) maxAmount);
        ItemStack remainder = be.extractItem(stack, true);
        if (remainder.isEmpty()) return 0;

        long extracted = stack.getCount() - remainder.getCount();
        transaction.addCloseCallback((ctx, result) -> {
            if (result.wasCommitted())
                be.extractItem(stack, false);
        });
        return extracted;
    }

    @Override
    public long insert(ItemVariant resource, long maxAmount, TransactionContext transaction) {
        ItemStack stack = resource.toStack((int) maxAmount);
        ItemStack remainder = be.addItemToInput(stack, true);
        if (remainder.getCount() == stack.getCount()) return 0;

        long inserted = stack.getCount() - remainder.getCount();
        transaction.addCloseCallback((ctx, result) -> {
            if (result.wasCommitted())
                be.addItemToInput(stack, false);
        });
        return inserted;
    }

    @Override
    public @NotNull Iterator<StorageView<ItemVariant>> iterator() {
        List<StorageView<ItemVariant>> views = new ArrayList<>();
        for (int i = 0; i < be.getContainerSize(); ++i) {
            ItemStack stack = be.getItem(i);
            if (stack.isEmpty()) continue;

            ItemVariant vari = ItemVariant.of(stack);
            StorageView<ItemVariant> view = new StorageView<>() {
                @Override
                public long extract(ItemVariant resource, long maxAmount, TransactionContext transaction) {
                    return 0;
                }

                @Override
                public boolean isResourceBlank() {
                    return false;
                }

                @Override
                public ItemVariant getResource() {
                    return vari;
                }

                @Override
                public long getAmount() {
                    return stack.getCount();
                }

                @Override
                public long getCapacity() {
                    return stack.getMaxStackSize();
                }
            };
            views.add(view);
        }
        return views.iterator();
    }


}
