package net.primaxstudios.primaxcore.menu;

import net.primaxstudios.primaxcore.event.menu.CustomMenuClickEvent;
import net.primaxstudios.primaxcore.event.menu.CustomMenuCloseEvent;
import net.primaxstudios.primaxcore.event.menu.CustomMenuDragEvent;
import net.primaxstudios.primaxcore.event.menu.CustomMenuOpenEvent;

public interface MenuHandler {

    default void onOpen(CustomMenuOpenEvent e) {}

    default void onClick(CustomMenuClickEvent e) {}

    default void onDrag(CustomMenuDragEvent e) {}

    default void onClose(CustomMenuCloseEvent e) {}
}
