package net.primaxstudios.primaxcore.menus;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.primaxstudios.primaxcore.PrimaxCore;
import net.primaxstudios.primaxcore.events.menu.CustomMenuClickEvent;
import net.primaxstudios.primaxcore.menus.item.ClickableItem;
import net.primaxstudios.primaxcore.menus.item.FillerItem;
import net.primaxstudios.primaxcore.menus.item.MenuItem;
import net.primaxstudios.primaxcore.menus.types.MenuType;
import net.primaxstudios.primaxcore.utils.ColorUtils;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter @Setter
public abstract class CustomMenu implements MenuHandler {

    private final List<MenuItem> menuItems = new ArrayList<>();
    private Component menuTitle;
    private MenuType menuType;

    public CustomMenu(MenuItem... menuItems) {
        if (getSettings().isAddFiller()) {
            this.menuItems.add(new FillerItem());
        }
        this.menuItems.addAll(Arrays.asList(menuItems));
    }

    public abstract File getFile();

    public MenuSettings getSettings() {
        return new MenuSettings();
    }

    public Inventory createInventory(MenuHolder holder) {
        return menuType.createInventory(holder, menuTitle);
    }

    public MenuHolder createHolder(Player player) {
        return new MenuHolder(player, this);
    }

    public void reload() {
        File file = getFile();
        
        YamlDocument document = ConfigUtils.load(file);

        menuTitle = ColorUtils.getComponent(document.getString("menu_title"));

        menuType = PrimaxCore.inst().getMenuManager().getMenuType(document);

        Section itemsSection = document.getSection("items");
        for (MenuItem item : menuItems) {
            Section section = itemsSection.getSection(item.getId());
            item.reload(section);
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

    public <T extends MenuItem> T getMenuItem(Class<T> itemClass) {
        for (MenuItem menuItem : menuItems) {
            if (menuItem.getClass().equals(itemClass)) {
                return (T) menuItem;
            }
        }
        return null;
    }
}
