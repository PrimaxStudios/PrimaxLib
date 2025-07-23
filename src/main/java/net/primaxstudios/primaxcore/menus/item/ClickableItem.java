package net.primaxstudios.primaxcore.menus.item;

import net.primaxstudios.primaxcore.events.menu.CustomMenuClickEvent;

public interface ClickableItem extends MenuItem {

    void onClick(CustomMenuClickEvent e);
}