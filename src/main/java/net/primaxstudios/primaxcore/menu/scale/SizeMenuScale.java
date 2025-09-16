package net.primaxstudios.primaxcore.menu.scale;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.primaxstudios.primaxcore.menu.MenuHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

@Getter
public class SizeMenuScale implements MenuScale {

    private final int size;

    public SizeMenuScale(int size) {
        this.size = size;
    }

    @Override
    public Inventory createInventory(MenuHolder holder, Component menuTitle) {
        return Bukkit.createInventory(holder, size, menuTitle);
    }
}
