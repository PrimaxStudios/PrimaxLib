package net.primaxstudios.primaxlib.menu.scale;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.primaxstudios.primaxlib.menu.MenuHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

@Getter
public class RowMenuScale implements MenuScale {

    private final int rows;

    public RowMenuScale(int rows) {
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
