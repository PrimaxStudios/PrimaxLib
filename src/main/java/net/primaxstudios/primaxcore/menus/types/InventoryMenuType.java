package net.primaxstudios.primaxcore.menus.types;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.primaxstudios.primaxcore.menus.MenuHolder;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

@Getter
public class InventoryMenuType implements MenuType {

    private final InventoryType type;

    public InventoryMenuType(InventoryType type) {
        this.type = type;
    }

    @Override
    public Inventory createInventory(MenuHolder holder, Component menuTitle) {
        return Bukkit.createInventory(holder, type, menuTitle);
    }
}
