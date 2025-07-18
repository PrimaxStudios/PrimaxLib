package net.primaxstudios.primaxcore.menus;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public interface MenuHandler {

    default void onOpen(InventoryOpenEvent e) {}

    default void onClick(InventoryClickEvent e) {}

    default void onDrag(InventoryDragEvent e) {}

    default void onClose(InventoryCloseEvent e) {}
}
