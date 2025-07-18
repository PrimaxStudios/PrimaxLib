package net.primaxstudios.primaxcore.menus;

import org.bukkit.inventory.Inventory;

public abstract class CustomMenu implements MenuHandler {

    protected abstract Inventory createInventory();

    protected abstract void refresh(Inventory inventory);

    public abstract void reload();
}
