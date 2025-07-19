package net.primaxstudios.primaxcore.events.menu;

import lombok.Getter;
import net.primaxstudios.primaxcore.menus.MenuHolder;
import net.primaxstudios.primaxcore.placeholders.Placeholder;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class CustomMenuUpdateEvent extends CustomMenuEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final Placeholder placeholder;

    public CustomMenuUpdateEvent(Player player, MenuHolder holder) {
        super(player, holder);
        this.placeholder = new Placeholder(player);
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
