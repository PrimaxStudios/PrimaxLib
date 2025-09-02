package net.primaxstudios.primaxcore.menus.item;

import net.primaxstudios.primaxcore.events.menu.CustomMenuClickEvent;
import net.primaxstudios.primaxcore.menus.item.slot.SingleSlotItem;
import org.bukkit.Bukkit;

public abstract class CloseItem extends SingleSlotItem implements ClickableItem {

    @Override
    public String getId() {
        return "close";
    }

    @Override
    public void onClick(CustomMenuClickEvent e) {
        Bukkit.getScheduler().runTask(getPlugin(), () -> e.getHolder().getPlayer().closeInventory());
    }
}
