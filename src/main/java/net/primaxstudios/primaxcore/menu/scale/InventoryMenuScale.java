package net.primaxstudios.primaxcore.menu.scale;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.primaxstudios.primaxcore.menu.MenuHolder;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

@Getter
public class InventoryMenuScale implements MenuScale {

    private final InventoryType type;

    public InventoryMenuScale(InventoryType type) {
        this.type = type;
    }

    @Override
    public Inventory createInventory(MenuHolder holder, Component menuTitle) {
        return Bukkit.createInventory(holder, type, menuTitle);
    }
}
