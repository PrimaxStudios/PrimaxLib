package net.primaxstudios.primaxlib.menu;

import net.primaxstudios.primaxlib.event.menu.CustomMenuClickEvent;
import net.primaxstudios.primaxlib.event.menu.CustomMenuCloseEvent;
import net.primaxstudios.primaxlib.event.menu.CustomMenuDragEvent;
import net.primaxstudios.primaxlib.event.menu.CustomMenuOpenEvent;

public interface MenuHandler {

    default void onOpen(CustomMenuOpenEvent e) {}

    default void onClick(CustomMenuClickEvent e) {}

    default void onDrag(CustomMenuDragEvent e) {}

    default void onClose(CustomMenuCloseEvent e) {}
}
