package net.primaxstudios.primaxcore.menus.item;

import lombok.Getter;
import net.primaxstudios.primaxcore.events.menu.CustomMenuClickEvent;
import net.primaxstudios.primaxcore.menus.MenuHolder;
import net.primaxstudios.primaxcore.menus.item.slot.SingleSlotItem;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class RefreshItem extends SingleSlotItem implements ClickableItem {

    private final JavaPlugin plugin;

    public RefreshItem(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getId() {
        return "refresh";
    }

    @Override
    public void onClick(CustomMenuClickEvent e) {
        MenuHolder holder = e.getHolder();
        holder.getCustomMenu().refresh(holder);
    }
}
