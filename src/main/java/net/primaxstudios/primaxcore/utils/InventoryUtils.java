package net.primaxstudios.primaxcore.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

    public static boolean isCustomInventory(Inventory inventory) {
        return inventory.getClass().getName().equals("org.bukkit.craftbukkit.inventory.CraftInventoryCustom");
    }

    public static Component getTitle(Inventory inventory) {
        try {
            Class<?> aClass = inventory.getClass();
            Method method = aClass.getDeclaredMethod("title");
            return (Component) method.invoke(inventory);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
