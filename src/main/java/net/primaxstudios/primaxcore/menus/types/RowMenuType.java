package net.primaxstudios.primaxcore.menus.types;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.primaxstudios.primaxcore.menus.MenuHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

@Getter
public class RowMenuType implements MenuType {

    private final int rows;

    public RowMenuType(int rows) {
        this.rows = rows;
    }

    @Override
    public Inventory createInventory(MenuHolder holder, Component menuTitle) {
        return Bukkit.createInventory(holder, getSize(), menuTitle);
    }

    public int getSize() {
        return rows * 9;
    }
}
