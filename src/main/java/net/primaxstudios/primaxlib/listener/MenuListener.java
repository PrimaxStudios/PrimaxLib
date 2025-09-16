package net.primaxstudios.primaxlib.listener;

import net.primaxstudios.primaxlib.event.menu.CustomMenuClickEvent;
import net.primaxstudios.primaxlib.event.menu.CustomMenuCloseEvent;
import net.primaxstudios.primaxlib.event.menu.CustomMenuDragEvent;
import net.primaxstudios.primaxlib.event.menu.CustomMenuOpenEvent;
import net.primaxstudios.primaxlib.menu.MenuHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;

public class MenuListener implements Listener {

    @EventHandler
    public void onOpen(InventoryOpenEvent e) {
        if (e.isCancelled()) return;

        MenuHolder holder = getMenuHolder(e.getInventory());
        if (holder == null) return;

        CustomMenuOpenEvent openEvent = new CustomMenuOpenEvent((Player) e.getPlayer(), holder, e);
        if (!openEvent.callEvent()) return;

        holder.getCustomMenu().onOpen(openEvent);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.isCancelled() || e.getClickedInventory() == null) return;

        InventoryAction action = e.getAction();
        if (action.equals(InventoryAction.NOTHING)) return;

        MenuHolder holder = getHolder(e.getInventory(), e.getClickedInventory());
        if (holder == null) return;

        CustomMenuClickEvent clickEvent = new CustomMenuClickEvent((Player) e.getWhoClicked(), holder, e);
        if (!clickEvent.callEvent()) return;

        holder.getCustomMenu().onClick(clickEvent);
    }

    private MenuHolder getHolder(Inventory inventory, Inventory clickedInventory) {
        MenuHolder holder = getMenuHolder(inventory);
        if (holder != null) {
            return holder;
        }
        return getMenuHolder(clickedInventory);
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        if (e.isCancelled()) return;

        MenuHolder holder = getMenuHolder(e.getInventory());
        if (holder == null) return;

        CustomMenuDragEvent dragEvent = new CustomMenuDragEvent((Player) e.getWhoClicked(), holder, e);
        if (!dragEvent.callEvent()) return;

        holder.getCustomMenu().onDrag(dragEvent);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        MenuHolder holder = getMenuHolder(e.getInventory());
        if (holder == null) return;

        CustomMenuCloseEvent closeEvent = new CustomMenuCloseEvent((Player) e.getPlayer(), holder, e);
        closeEvent.callEvent();
        holder.getCustomMenu().onClose(closeEvent);
    }

    private MenuHolder getMenuHolder(Inventory inventory) {
        if (!(inventory.getHolder() instanceof MenuHolder holder)) {
            return null;
        }
        return holder;
    }
}