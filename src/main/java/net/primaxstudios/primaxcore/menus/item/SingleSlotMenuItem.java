package net.primaxstudios.primaxcore.menus.item;

import lombok.Getter;
import net.primaxstudios.primaxcore.items.CustomItem;
import org.bukkit.inventory.Inventory;

@Getter
public class SingleSlotMenuItem extends MenuItem {

    private final int slot;

    public SingleSlotMenuItem(CustomItem customItem, int slot) {
        super(customItem);
        this.slot = slot;
    }

    @Override
    public boolean isSlot(int slot) {
        return this.slot == slot;
    }

    @Override
    public void setItem(Inventory inventory) {
        inventory.setItem(slot, getCustomItem().getItem());
    }
}
