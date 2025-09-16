package net.primaxstudios.primaxcore.menus.item.impl;

import lombok.Getter;
import net.primaxstudios.primaxcore.events.menu.CustomMenuClickEvent;
import net.primaxstudios.primaxcore.menus.ClickResult;
import net.primaxstudios.primaxcore.menus.item.SingleSlotItem;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class CloseItem extends SingleSlotItem {

    private final JavaPlugin plugin;

    public CloseItem(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getId() {
        return "close";
    }

    @Override
    public ClickResult onClick(CustomMenuClickEvent e) {
        Bukkit.getScheduler().runTask(plugin, () -> e.getHolder().getPlayer().closeInventory());
        return ClickResult.SUCCESS;
    }
}
