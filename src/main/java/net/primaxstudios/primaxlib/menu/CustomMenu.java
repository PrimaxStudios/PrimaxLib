package net.primaxstudios.primaxlib.menu;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.primaxstudios.primaxlib.configuration.Config;
import net.primaxstudios.primaxlib.event.menu.CustomMenuClickEvent;
import net.primaxstudios.primaxlib.event.menu.CustomMenuDragEvent;
import net.primaxstudios.primaxlib.locale.Locale;
import net.primaxstudios.primaxlib.menu.item.impl.FillerItem;
import net.primaxstudios.primaxlib.menu.item.MenuItem;
import net.primaxstudios.primaxlib.menu.item.OptionalItem;
import net.primaxstudios.primaxlib.menu.scale.MenuScale;
import net.primaxstudios.primaxlib.util.ColorUtils;
import net.primaxstudios.primaxlib.util.ConfigUtils;
import net.primaxstudios.primaxlib.util.MenuUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter @Setter
public abstract class CustomMenu implements MenuHandler {

    private final List<MenuItem> menuItems = new ArrayList<>();
    private Component menuTitle;
    private MenuScale menuType;

    public CustomMenu(MenuItem... menuItems) {
        if (getSettings().isAddFiller()) {
            this.menuItems.add(new FillerItem(getPlugin()));
        }
        this.menuItems.addAll(Arrays.asList(menuItems));
    }

    public abstract JavaPlugin getPlugin();

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
        try {
            File file = Objects.requireNonNull(getFile(), "File cannot be null");
            YamlDocument document = ConfigUtils.load(file);
            reload(document);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void reload(Section document) {
        menuTitle = ColorUtils.getComponent(Config.requireNonNull(ConfigUtils.getString(document, "menu_title"), document, "Menu title is missing"));
        menuType = Config.requireNonNull(MenuUtils.createMenuScale(document), document, "Menu Type is missing");

        Section itemsSection = document.getSection("items");
        if (!menuItems.isEmpty()) Config.requireNonNull(itemsSection, document, "'items' section is missing");

        for (MenuItem item : menuItems) {
            Section section = Config.requireNonNull(itemsSection.getSection(item.getId()), itemsSection, "'" + item.getId() + "'" + "section is missing");
            item.reload(section);
        }
    }

    public void refresh(MenuHolder holder, Class<? extends MenuItem> mClass) {
        Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), () -> {
            for (MenuItem item : getMenuItems(mClass)) {
                if (item instanceof OptionalItem optional && !optional.isEnabled()) continue;
                try {
                    item.setItem(holder);
                } catch (Exception ex) {
                    Player player = holder.getPlayer();
                    Locale.inst().sendMessage(player, "error_occurred");
                    Bukkit.getScheduler().runTask(getPlugin(), () -> player.closeInventory());
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void refresh(MenuHolder holder) {
        Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), () -> {
            for (MenuItem item : menuItems) {
                if (item instanceof OptionalItem optional && !optional.isEnabled()) continue;
                try {
                    item.setItem(holder);
                } catch (Exception ex) {
                    Player player = holder.getPlayer();
                    Locale.inst().sendMessage(player, "error_occurred");
                    Bukkit.getScheduler().runTask(getPlugin(), () -> player.closeInventory());
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    @Override
    public void onClick(CustomMenuClickEvent e) {
        if (getSettings().isCancelClickEvent()) {
            e.setCancelled(true);
        }
        for (MenuItem item : menuItems) {
            if (item instanceof OptionalItem optional && !optional.isEnabled()) continue;
            if (!item.isSlot(e.getOriginalEvent().getSlot()))
                continue;
            try {
                item.click(e);
            } catch (Exception ex) {
                Player player = e.getHolder().getPlayer();
                Locale.inst().sendMessage(player, "error_occurred");
                Bukkit.getScheduler().runTask(getPlugin(), () -> player.closeInventory());
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void onDrag(CustomMenuDragEvent e) {
        if (getSettings().isCancelDragEvent()) {
            e.setCancelled(true);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends MenuItem> T getMenuItem(Class<T> itemClass) {
        for (MenuItem menuItem : menuItems) {
            if (menuItem.getClass().equals(itemClass)) {
                return (T) menuItem;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T extends MenuItem> List<T> getMenuItems(Class<T> itemClass) {
        List<T> items = new ArrayList<>();
        for (MenuItem menuItem : menuItems) {
            if (menuItem.getClass().equals(itemClass)) {
                items.add((T) menuItem);
            }
        }
        return items;
    }
}
