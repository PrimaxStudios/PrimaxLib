package net.primaxstudios.primaxlib.menu.item.impl;

import lombok.Getter;
import net.primaxstudios.primaxlib.event.menu.CustomMenuClickEvent;
import net.primaxstudios.primaxlib.menu.MenuHolder;
import net.primaxstudios.primaxlib.menu.MenuSound;
import net.primaxstudios.primaxlib.menu.ClickResult;
import net.primaxstudios.primaxlib.menu.item.SingleSlotItem;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class RefreshItem extends SingleSlotItem {

    private final JavaPlugin plugin;

    public RefreshItem(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getId() {
        return "refresh";
    }

    @Override
    public MenuSound getSound() {
        return null;
    }

    @Override
    public ClickResult onClick(CustomMenuClickEvent e) {
        MenuHolder holder = e.getHolder();
        holder.getCustomMenu().refresh(holder);
        return ClickResult.SUCCESS;
    }
}
