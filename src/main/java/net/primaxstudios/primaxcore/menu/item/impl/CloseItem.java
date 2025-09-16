package net.primaxstudios.primaxcore.menu.item.impl;

import lombok.Getter;
import net.primaxstudios.primaxcore.event.menu.CustomMenuClickEvent;
import net.primaxstudios.primaxcore.menu.ClickResult;
import net.primaxstudios.primaxcore.menu.item.SingleSlotItem;
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
