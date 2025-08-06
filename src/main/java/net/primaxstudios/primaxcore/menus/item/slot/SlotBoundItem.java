package net.primaxstudios.primaxcore.menus.item.slot;

import net.primaxstudios.primaxcore.menus.item.MenuItem;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface SlotBoundItem extends MenuItem {

    List<Integer> getSlots();

    @Override
    default boolean isSlot(int slot) {
        return getSlots().contains(slot);
    }

    @Override
    default void setItem(Inventory inventory, ItemStack item) {
        for (int slot : getSlots()) {
            inventory.setItem(slot, item);
        }
    }

    default void clear(Inventory inventory) {
        for (int slot : getSlots()) {
            inventory.setItem(slot, null);
        }
    }
}
