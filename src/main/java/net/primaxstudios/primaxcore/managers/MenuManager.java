package net.primaxstudios.primaxcore.managers;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import net.primaxstudios.primaxcore.menus.MenuHolder;
import net.primaxstudios.primaxcore.menus.types.InventoryMenuType;
import net.primaxstudios.primaxcore.menus.types.MenuType;
import net.primaxstudios.primaxcore.menus.types.RowMenuType;
import net.primaxstudios.primaxcore.menus.types.SizeMenuType;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

@Getter
public class MenuManager {

    private static final Logger logger = LoggerFactory.getLogger(MenuManager.class);

    public MenuHolder getMenuHolder(Inventory inventory) {
        if (!(inventory.getHolder() instanceof MenuHolder holder)) {
            return null;
        }
        return holder;
    }

    public MenuType getMenuType(Section section) {
        if (section.contains("menu_size")) {
            int size = section.getInt("menu_size");
            return new SizeMenuType(size);
        }else if (section.contains("menu_rows")) {
            int rows = section.getInt("menu_rows");
            return new RowMenuType(rows);
        }else if (section.contains("menu_type")) {
            InventoryType type = InventoryType.valueOf(section.getString("menu_type").toUpperCase(Locale.ROOT));
            return new InventoryMenuType(type);
        }else {
            logger.warn("Missing 'size' or 'rows' key in section '{}' of '{}'", section.getName(), section.getRoot().getFile());
            throw new RuntimeException();
        }
    }
}
