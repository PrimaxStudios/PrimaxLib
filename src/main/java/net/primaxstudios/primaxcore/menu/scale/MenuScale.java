package net.primaxstudios.primaxcore.menu.scale;

import net.kyori.adventure.text.Component;
import net.primaxstudios.primaxcore.menu.MenuHolder;
import org.bukkit.inventory.Inventory;

public interface MenuScale {

    Inventory createInventory(MenuHolder holder, Component menuTitle);
}
