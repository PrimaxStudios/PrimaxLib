package net.primaxstudios.primaxcore.menus.item;

import net.primaxstudios.primaxcore.menus.MenuHolder;

import java.util.List;

public interface PaginatedItem<T> extends MenuItem {

    List<T> getPageItems(MenuHolder holder);

    void setItem(MenuHolder holder, int slot, T item);

    List<Integer> getDisplaySlots(MenuHolder holder);

    @Override
    default void setItem(MenuHolder holder) {
        List<T> items = getPageItems(holder);
        List<Integer> slots = getDisplaySlots(holder);

        for (int i = 0; i < Math.min(items.size(), slots.size()); i++) {
            setItem(holder, slots.get(i), items.get(i));
        }
    }
}