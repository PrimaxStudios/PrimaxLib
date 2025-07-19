package net.primaxstudios.primaxcore.menus;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public abstract class CustomMenu implements MenuHandler {

    public abstract MenuHolder createHolder(Player player);

    public abstract Inventory createInventory(MenuHolder holder);

    public abstract void refresh(MenuHolder holder);

    public abstract void reload();
}
