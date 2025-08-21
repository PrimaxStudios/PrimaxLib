package net.primaxstudios.primaxcore.menus.item;

import net.primaxstudios.primaxcore.events.menu.CustomMenuClickEvent;
import org.bukkit.Sound;

public interface ClickableItem extends MenuItem {

//    Sound getSound();
//
//    default void click() {
//
//    }

    void onClick(CustomMenuClickEvent e);
}