package net.primaxstudios.primaxcore.menus.types;

import net.kyori.adventure.text.Component;
import net.primaxstudios.primaxcore.menus.MenuHolder;
import org.bukkit.inventory.Inventory;

public interface MenuType {

    Inventory createInventory(MenuHolder holder, Component menuTitle);
}
