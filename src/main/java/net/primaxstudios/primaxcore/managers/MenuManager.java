package net.primaxstudios.primaxcore.managers;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import net.primaxstudios.primaxcore.PrimaxCore;
import net.primaxstudios.primaxcore.listeners.MenuListener;
import net.primaxstudios.primaxcore.menus.MenuHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class MenuManager {

    private static final Logger logger = LoggerFactory.getLogger(MenuManager.class);
    private final MenuItemManager itemManager = new MenuItemManager();

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
}
