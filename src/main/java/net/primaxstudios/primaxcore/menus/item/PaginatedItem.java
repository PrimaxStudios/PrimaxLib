package net.primaxstudios.primaxcore.menus.item;

import net.primaxstudios.primaxcore.events.menu.CustomMenuClickEvent;
import net.primaxstudios.primaxcore.menus.MenuHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PaginatedItem<T> extends MenuItem, ClickableItem {

    List<Integer> getSlots();

    void setItem(MenuHolder holder, int slot, T object);

    Map<Integer, T> getObjectBySlot(MenuHolder holder);

    void setObjectBySlot(MenuHolder holder, Map<Integer, T> objectBySlot);

    List<T> getObjects();

    void onClick(CustomMenuClickEvent e, T object);

    @Override
    default void setItem(MenuHolder holder) {
        Map<Integer, T> objectBySlot = new HashMap<>();

        List<T> objects = getObjects();
        List<Integer> slots = getSlots();
        for (int i = 0; i < Math.min(objects.size(), slots.size()); i++) {
            T object = objects.get(i);
            objectBySlot.put(i, object);
            setItem(holder, slots.get(i), object);
        }

        setObjectBySlot(holder, objectBySlot);
    }

    @Override
    default void onClick(CustomMenuClickEvent e) {
        MenuHolder holder = e.getHolder();
        int slot = e.getOriginalEvent().getSlot();

        Map<Integer, T> objectBySlot = getObjectBySlot(holder);
        if (objectBySlot.isEmpty()) return;

        T object = objectBySlot.get(slot);
        if (object == null) return;

        onClick(e, object);
    }
}