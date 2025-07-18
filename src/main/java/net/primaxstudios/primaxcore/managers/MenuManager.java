package net.primaxstudios.primaxcore.managers;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import net.primaxstudios.primaxcore.PrimaxCore;
import net.primaxstudios.primaxcore.items.CustomItem;
import net.primaxstudios.primaxcore.listeners.MenuListener;
import net.primaxstudios.primaxcore.menus.MenuHolder;
import net.primaxstudios.primaxcore.menus.item.MenuItem;
import net.primaxstudios.primaxcore.menus.item.MultiSlotMenuItem;
import net.primaxstudios.primaxcore.menus.item.SingleSlotMenuItem;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Getter
public class MenuManager {

    private static final Logger logger = LoggerFactory.getLogger(MenuManager.class);

    public MenuManager() {
        Bukkit.getServer().getPluginManager().registerEvents(new MenuListener(this), PrimaxCore.inst());
    }

    public MenuHolder getMenuHolder(Inventory inventory) {
        if (!(inventory.getHolder() instanceof MenuHolder holder)) {
            return null;
        }
        return holder;
    }

    public int getSize(Section section) {
        if (section.contains("size")) {
            return section.getInt("size");
        }else if (section.contains("rows")) {
            return section.getInt("rows");
        }else {
            logger.warn("Missing 'size' or 'rows' key in section '{}' of '{}'", section.getName(), section.getRoot().getFile());
            throw new RuntimeException();
        }
    }

    public MenuItem getMenuItem(Section section) {
        if (section.contains("slot")) {
            return getSingleSlotMenuItem(section);
        } else if (section.contains("slots")) {
            return getMultiSlotMenuItem(section);
        }else {
            logger.warn("Missing 'slot' or 'slots' key in section '{}' of '{}'", section.getName(), section.getRoot().getFile());
            throw new RuntimeException();
        }
    }

    public SingleSlotMenuItem getSingleSlotMenuItem(Section section) {
        CustomItem customItem = PrimaxCore.inst().getItemManager().getItem(section);
        if (!section.contains("slot")) {
            logger.warn("Missing 'slot' key in section '{}' of '{}'", section.getName(), section.getRoot().getFile());
            throw new RuntimeException();
        }
        int slot = section.getInt("slot");
        return new SingleSlotMenuItem(customItem, slot);
    }

    public MultiSlotMenuItem getMultiSlotMenuItem(Section section) {
        CustomItem customItem = PrimaxCore.inst().getItemManager().getItem(section);
        List<Integer> slots = ConfigUtils.parseSlots(section, "slots");
        return new MultiSlotMenuItem(customItem, slots);
    }

    public List<MenuItem> getMenuItems(Section itemsSection) {
        return itemsSection.getRoutesAsStrings(false).stream()
                .map((route) -> getMenuItem(itemsSection.getSection(route)))
                .toList();
    }
}
