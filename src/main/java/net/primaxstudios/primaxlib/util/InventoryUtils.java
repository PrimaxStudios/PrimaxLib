package net.primaxstudios.primaxlib.util;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class InventoryUtils {

    private InventoryUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static int getSpace(@NotNull Inventory inventory, ItemStack item) {
        int remainingSpace = 0;
        for (ItemStack content : inventory.getContents()) {
            if (content == null) {
                remainingSpace += item.getMaxStackSize();
            } else if (content.isSimilar(item)) {
                int space = item.getMaxStackSize() - content.getAmount();
                remainingSpace += space;
            }
        }
        return remainingSpace;
    }

    public static boolean hasSpace(@NotNull Inventory inventory, ItemStack item, int amount) {
        return getSpace(inventory, item) >= amount;
    }
}
