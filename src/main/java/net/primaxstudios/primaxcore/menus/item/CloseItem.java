package net.primaxstudios.primaxcore.menus.item;

import lombok.Getter;
import net.primaxstudios.primaxcore.events.menu.CustomMenuClickEvent;
import net.primaxstudios.primaxcore.menus.item.slot.SingleSlotItem;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class CloseItem extends SingleSlotItem implements ClickableItem {

    private final JavaPlugin plugin;

    public CloseItem(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getId() {
        return "close";
    }

    @Override
    public void onClick(CustomMenuClickEvent e) {
        Bukkit.getScheduler().runTask(getPlugin(), () -> e.getHolder().getPlayer().closeInventory());
    }
}
