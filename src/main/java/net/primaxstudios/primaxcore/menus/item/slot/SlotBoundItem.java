package net.primaxstudios.primaxcore.menus.item.slot;

import net.primaxstudios.primaxcore.menus.item.AbstractMenuItem;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class SlotBoundItem extends AbstractMenuItem {

    public abstract List<Integer> getBoundSlots();

    @Override
    public boolean isSlot(int slot) {
        return getBoundSlots().contains(slot);
    }

    @Override
    public void setItem(Inventory inventory, ItemStack item) {
        for (int slot : getBoundSlots()) {
            inventory.setItem(slot, item);
        }
    }
}
