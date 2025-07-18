package net.primaxstudios.primaxcore.menus.item;

import lombok.Getter;
import net.primaxstudios.primaxcore.items.CustomItem;
import org.bukkit.inventory.Inventory;

import java.util.List;

@Getter
public class MultiSlotMenuItem extends MenuItem {

    private final List<Integer> slots;

    public MultiSlotMenuItem(CustomItem customItem, List<Integer> slots) {
        super(customItem);
        this.slots = slots;
    }

    @Override
    public boolean isSlot(int slot) {
        return this.slots.contains(slot);
    }

    @Override
    public void setItem(Inventory inventory) {
        for (int slot : slots) {
            inventory.setItem(slot, getCustomItem().getItem());
        }
    }
}
