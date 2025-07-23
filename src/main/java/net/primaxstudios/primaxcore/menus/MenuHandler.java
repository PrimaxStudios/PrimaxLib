package net.primaxstudios.primaxcore.menus;

import net.primaxstudios.primaxcore.events.menu.CustomMenuClickEvent;
import net.primaxstudios.primaxcore.events.menu.CustomMenuCloseEvent;
import net.primaxstudios.primaxcore.events.menu.CustomMenuDragEvent;
import net.primaxstudios.primaxcore.events.menu.CustomMenuOpenEvent;

public interface MenuHandler {

    default void onOpen(CustomMenuOpenEvent e) {}

    default void onClick(CustomMenuClickEvent e) {}

    default void onDrag(CustomMenuDragEvent e) {}

    default void onClose(CustomMenuCloseEvent e) {}
}
