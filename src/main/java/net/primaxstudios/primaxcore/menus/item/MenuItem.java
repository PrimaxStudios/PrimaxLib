package net.primaxstudios.primaxcore.menus.item;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxcore.menus.MenuHolder;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface MenuItem {

    String getId();

    boolean isEnabled();

    void reload(Section section);

    boolean isSlot(int slot);

    void setItem(Inventory inventory, ItemStack item);

    void setItem(MenuHolder holder);
}