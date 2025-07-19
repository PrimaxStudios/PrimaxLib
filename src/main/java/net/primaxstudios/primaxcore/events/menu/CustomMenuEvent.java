package net.primaxstudios.primaxcore.events.menu;

import lombok.Getter;
import net.primaxstudios.primaxcore.menus.MenuHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Getter
public abstract class CustomMenuEvent extends Event {

    private final Player player;
    private final MenuHolder holder;

    public CustomMenuEvent(Player player, MenuHolder holder) {
        this.player = player;
        this.holder = holder;
    }
}
