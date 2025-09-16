package net.primaxstudios.primaxcore.event.menu;

import lombok.Getter;
import net.primaxstudios.primaxcore.menu.MenuHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.jetbrains.annotations.NotNull;

@Getter
public class CustomMenuDragEvent extends CustomMenuEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final InventoryDragEvent originalEvent;

    public CustomMenuDragEvent(Player player, MenuHolder holder, InventoryDragEvent originalEvent) {
        super(player, holder);
        this.originalEvent = originalEvent;
    }

    @Override
    public boolean isCancelled() {
        return originalEvent.isCancelled();
    }

    @Override
    public void setCancelled(boolean cancel) {
        originalEvent.setCancelled(cancel);
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
