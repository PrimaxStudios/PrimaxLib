package net.primaxstudios.primaxcore.event.menu;

import lombok.Getter;
import net.primaxstudios.primaxcore.menu.MenuHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class CustomMenuUpdateEvent extends CustomMenuEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public CustomMenuUpdateEvent(Player player, MenuHolder holder) {
        super(player, holder);
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
