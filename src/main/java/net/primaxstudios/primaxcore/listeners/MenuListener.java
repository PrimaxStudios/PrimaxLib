package net.primaxstudios.primaxcore.listeners;

import net.primaxstudios.primaxcore.managers.MenuManager;
import net.primaxstudios.primaxcore.menus.MenuHolder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;

public class MenuListener implements Listener {

    private final MenuManager menuManager;

    public MenuListener(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent e) {
        if (e.isCancelled()) {
            return;
        }
        MenuHolder holder = menuManager.getMenuHolder(e.getInventory());
        if (holder == null) {
            return;
        }
        holder.getCustomMenu().onOpen(e);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.isCancelled() || e.getClickedInventory() == null) {
            return;
        }
        InventoryAction action = e.getAction();
        if (action.equals(InventoryAction.NOTHING)) {
            return;
        }
        MenuHolder holder = menuManager.getMenuHolder(e.getInventory());
        if (holder == null) {
            return;
        }
        holder.getCustomMenu().onClick(e);
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        if (e.isCancelled()) {
            return;
        }
        MenuHolder holder = menuManager.getMenuHolder(e.getInventory());
        if (holder == null) {
            return;
        }
        holder.getCustomMenu().onDrag(e);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        MenuHolder holder = menuManager.getMenuHolder(e.getInventory());
        if (holder == null) {
            return;
        }
        holder.getCustomMenu().onClose(e);
    }
}