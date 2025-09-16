package net.primaxstudios.primaxlib.event.menu;

import lombok.Getter;
import net.primaxstudios.primaxlib.menu.MenuHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;

@Getter
public class CustomMenuCloseEvent extends CustomMenuEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final InventoryCloseEvent originalEvent;

    public CustomMenuCloseEvent(Player player, MenuHolder holder, InventoryCloseEvent originalEvent) {
        super(player, holder);
        this.originalEvent = originalEvent;
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
