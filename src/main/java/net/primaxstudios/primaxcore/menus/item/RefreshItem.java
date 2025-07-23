package net.primaxstudios.primaxcore.menus.item;

import net.primaxstudios.primaxcore.events.menu.CustomMenuClickEvent;
import net.primaxstudios.primaxcore.menus.MenuHolder;
import net.primaxstudios.primaxcore.menus.item.slot.SingleSlotItem;

public class RefreshItem extends SingleSlotItem implements ClickableItem {

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
