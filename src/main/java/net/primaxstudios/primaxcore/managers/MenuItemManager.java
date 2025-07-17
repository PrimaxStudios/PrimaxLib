package net.primaxstudios.primaxcore.managers;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxcore.PrimaxCore;
import net.primaxstudios.primaxcore.items.CustomItem;
import net.primaxstudios.primaxcore.menus.item.MenuItem;
import net.primaxstudios.primaxcore.menus.item.MultiSlotMenuItem;
import net.primaxstudios.primaxcore.menus.item.SingleSlotMenuItem;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import net.primaxstudios.primaxcore.utils.Operation;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MenuItemManager {

    private static final Logger logger = LoggerFactory.getLogger(MenuItemManager.class);

    public MenuItem getMenuItem(JavaPlugin plugin, Section section) {
        if (section.contains("slot")) {
            return getSingleSlotMenuItem(plugin, section);
        } else if (section.contains("slots")) {
            return getMultiSlotMenuItem(plugin, section);
        }else {
            logger.warn("Missing 'slot' or 'slots' key in section '{}' of '{}'", section.getName(), section.getRoot().getFile());
            throw new RuntimeException();
        }
    }

    public SingleSlotMenuItem getSingleSlotMenuItem(JavaPlugin plugin, Section section) {
        CustomItem customItem = PrimaxCore.inst().getItemManager().getItem(plugin, section);

        if (!section.contains("slot")) {
            logger.warn("Missing 'slot' key in section '{}' of '{}'", section.getName(), section.getRoot().getFile());
            throw new RuntimeException();
        }
        int slot = section.getInt("slot");

        SingleSlotMenuItem menuItem = new SingleSlotMenuItem(customItem, slot);
        createMenuItem(menuItem, plugin, section);
        return menuItem;
    }

    public MultiSlotMenuItem getMultiSlotMenuItem(JavaPlugin plugin, Section section) {
        CustomItem customItem = PrimaxCore.inst().getItemManager().getItem(plugin, section);
        List<Integer> slots = ConfigUtils.parseSlots(section, "slots");
        MultiSlotMenuItem menuItem = new MultiSlotMenuItem(customItem, slots);
        createMenuItem(menuItem, plugin, section);
        return menuItem;
    }

    private void createMenuItem(MenuItem menuItem, JavaPlugin plugin, Section section) {
        if (section.contains("priority")) {
            menuItem.setPriority(section.getInt("priority"));
        }
        if (section.contains("update")) {
            menuItem.setUpdate(section.getBoolean("update"));
        }
        if (section.contains("view-requirements")) {
            menuItem.setViewRequirement(PrimaxCore.inst().getRequirementManager().getRequirement(plugin, section.getSection("view-requirements")));
        }
        Operation clickOperation = Operation.getOperation(plugin, section, "click");
        if (clickOperation != null) {
            menuItem.setClickOperation(clickOperation);
        }
        Operation rightClickOperation = Operation.getOperation(plugin, section, "right-click");
        if (rightClickOperation != null) {
            menuItem.setRightClickOperation(rightClickOperation);
        }
        Operation shiftRightClickOperation = Operation.getOperation(plugin, section, "shift-right-click");
        if (shiftRightClickOperation != null) {
            menuItem.setShiftRightClickOperation(shiftRightClickOperation);
        }
        Operation leftClickOperation = Operation.getOperation(plugin, section, "left-click");
        if (leftClickOperation != null) {
            menuItem.setLeftClickOperation(leftClickOperation);
        }
        Operation shiftLeftClickOperation = Operation.getOperation(plugin, section, "shift-left-click");
        if (shiftLeftClickOperation != null) {
            menuItem.setShiftLeftClickOperation(shiftLeftClickOperation);
        }
    }

    public List<MenuItem> getMenuItems(JavaPlugin plugin, Section itemsSection) {
        return itemsSection.getRoutesAsStrings(false).stream()
                .map((route) -> getMenuItem(plugin, itemsSection.getSection(route)))
                .toList();
    }
}