package net.primaxstudios.primaxcore.menus.types;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.primaxstudios.primaxcore.menus.MenuHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

@Getter
public class SizeMenuType implements MenuType {

    private final int size;

    public SizeMenuType(int size) {
        this.size = size;
    }

    @Override
    public Inventory createInventory(MenuHolder holder, Component menuTitle) {
        return Bukkit.createInventory(holder, size, menuTitle);
    }
}
