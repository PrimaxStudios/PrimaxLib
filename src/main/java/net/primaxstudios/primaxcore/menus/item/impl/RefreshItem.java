package net.primaxstudios.primaxcore.menus.item.impl;

import lombok.Getter;
import net.primaxstudios.primaxcore.events.menu.CustomMenuClickEvent;
import net.primaxstudios.primaxcore.menus.MenuHolder;
import net.primaxstudios.primaxcore.menus.MenuSound;
import net.primaxstudios.primaxcore.menus.ClickResult;
import net.primaxstudios.primaxcore.menus.item.SingleSlotItem;
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
