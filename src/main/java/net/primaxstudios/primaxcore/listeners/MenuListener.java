package net.primaxstudios.primaxcore.listeners;

import net.primaxstudios.primaxcore.events.menu.CustomMenuClickEvent;
import net.primaxstudios.primaxcore.events.menu.CustomMenuCloseEvent;
import net.primaxstudios.primaxcore.events.menu.CustomMenuDragEvent;
import net.primaxstudios.primaxcore.events.menu.CustomMenuOpenEvent;
import net.primaxstudios.primaxcore.managers.MenuManager;
import net.primaxstudios.primaxcore.menus.MenuHolder;
import org.bukkit.entity.Player;
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
        CustomMenuOpenEvent openEvent = new CustomMenuOpenEvent((Player) e.getPlayer(), holder, e);
        openEvent.callEvent();
        if (openEvent.isCancelled()) {
            return;
        }
        holder.getCustomMenu().onOpen(openEvent);
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
        CustomMenuClickEvent clickEvent = new CustomMenuClickEvent((Player) e.getWhoClicked(), holder, e);
        clickEvent.callEvent();
        if (clickEvent.isCancelled()) {
            return;
        }
        holder.getCustomMenu().onClick(clickEvent);
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
        CustomMenuDragEvent dragEvent = new CustomMenuDragEvent((Player) e.getWhoClicked(), holder, e);
        dragEvent.callEvent();
        if (dragEvent.isCancelled()) {
            return;
        }
        holder.getCustomMenu().onDrag(dragEvent );
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        MenuHolder holder = menuManager.getMenuHolder(e.getInventory());
        if (holder == null) {
            return;
        }
        CustomMenuCloseEvent closeEvent = new CustomMenuCloseEvent((Player) e.getPlayer(), holder, e);
        closeEvent.callEvent();
        holder.getCustomMenu().onClose(closeEvent);
    }
}