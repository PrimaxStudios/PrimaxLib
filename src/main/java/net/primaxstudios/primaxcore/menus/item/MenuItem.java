package net.primaxstudios.primaxcore.menus.item;

import lombok.Getter;
import net.primaxstudios.primaxcore.items.CustomItem;
import net.primaxstudios.primaxcore.menus.MenuHandler;
import net.primaxstudios.primaxcore.menus.MenuHolder;

@Getter
public abstract class MenuItem implements MenuHandler {

    private final CustomItem customItem;

    public MenuItem(CustomItem customItem) {
        this.customItem = customItem;
    }

    public abstract boolean isSlot(int slot);

    public abstract void setItem(MenuHolder holder);
}