package net.primaxstudios.primaxcore.menus;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import net.primaxstudios.primaxcore.events.menu.CustomMenuClickEvent;
import net.primaxstudios.primaxcore.menus.item.ClickableItem;
import net.primaxstudios.primaxcore.menus.item.FillerItem;
import net.primaxstudios.primaxcore.menus.item.MenuItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public abstract class CustomMenu implements MenuHandler {

    private final List<MenuItem> menuItems = new ArrayList<>();

    public CustomMenu(MenuItem... menuItems) {
        if (getSettings().isAddFiller()) {
            this.menuItems.add(new FillerItem());
        }
        this.menuItems.addAll(Arrays.asList(menuItems));
    }

    public abstract Inventory createInventory(MenuHolder holder);

    public MenuSettings getSettings() {
        return new MenuSettings();
    }

    public MenuHolder createHolder(Player player) {
        return new MenuHolder(player, this);
    }

    public void reload(Section section) {
        for (MenuItem item : menuItems) {
            item.reload(section.getSection(item.getId()));
        }
    }

    public void refresh(MenuHolder holder) {
        for (MenuItem item : menuItems) {
            if (item.isEnabled()) {
                item.setItem(holder);
            }
        }
    }

    @Override
    public void onClick(CustomMenuClickEvent e) {
        e.setCancelled(true);
        for (MenuItem item : menuItems) {
            if (item.isEnabled() && item instanceof ClickableItem clickable && item.isSlot(e.getOriginalEvent().getSlot())) {
                clickable.onClick(e);
            }
        }
    }
}
