package net.primaxstudios.primaxcore.events.menu;

import lombok.Getter;
import net.primaxstudios.primaxcore.menus.MenuHolder;
import net.primaxstudios.primaxcore.placeholders.Placeholder;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

@Getter
public class CustomMenuClickEvent extends CustomMenuEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final InventoryClickEvent originalEvent;
    private final Placeholder placeholder;

    public CustomMenuClickEvent(Player player, MenuHolder holder, InventoryClickEvent originalEvent) {
        super(player, holder);
        this.originalEvent = originalEvent;
        this.placeholder = new Placeholder(player);
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
