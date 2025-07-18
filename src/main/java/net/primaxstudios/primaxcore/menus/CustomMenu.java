package net.primaxstudios.primaxcore.menus;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public abstract class CustomMenu implements MenuHandler {

    protected abstract Inventory createInventory(Player player);

    protected abstract void refresh(Inventory inventory);

    public abstract void reload();
}
