package net.primaxstudios.primaxcore.menu.item.impl;

import lombok.Getter;
import net.primaxstudios.primaxcore.event.menu.CustomMenuClickEvent;
import net.primaxstudios.primaxcore.menu.MenuHolder;
import net.primaxstudios.primaxcore.menu.MenuSound;
import net.primaxstudios.primaxcore.menu.ClickResult;
import net.primaxstudios.primaxcore.menu.item.SingleSlotItem;
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
