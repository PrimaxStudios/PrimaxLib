package net.primaxstudios.primaxlib.event.menu;

import lombok.Getter;
import net.primaxstudios.primaxlib.menu.MenuHolder;
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
