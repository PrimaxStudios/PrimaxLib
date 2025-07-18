package net.primaxstudios.primaxcore.menus.item;

import lombok.Getter;
import net.primaxstudios.primaxcore.items.CustomItem;
import org.bukkit.inventory.Inventory;

@Getter
public abstract class MenuItem {

    private final CustomItem customItem;

    public MenuItem(CustomItem customItem) {
        this.customItem = customItem;
    }

    public abstract boolean isSlot(int slot);

    public abstract void setItem(Inventory inventory);
}