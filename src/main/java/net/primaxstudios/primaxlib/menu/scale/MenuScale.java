package net.primaxstudios.primaxlib.menu.scale;

import net.kyori.adventure.text.Component;
import net.primaxstudios.primaxlib.menu.MenuHolder;
import org.bukkit.inventory.Inventory;

public interface MenuScale {

    Inventory createInventory(MenuHolder holder, Component menuTitle);
}
