package net.primaxstudios.primaxcore.managers;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import net.primaxstudios.primaxcore.PrimaxCore;
import net.primaxstudios.primaxcore.listeners.MenuListener;
import net.primaxstudios.primaxcore.menus.CoreMenu;
import net.primaxstudios.primaxcore.menus.CustomMenu;
import net.primaxstudios.primaxcore.menus.MenuHolder;
import net.primaxstudios.primaxcore.menus.ReferenceMenu;
import net.primaxstudios.primaxcore.placeholders.objects.PlaceholderComponent;
import net.primaxstudios.primaxcore.registries.MenuRegistry;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import net.primaxstudios.primaxcore.utils.Key;
import net.primaxstudios.primaxcore.utils.Operation;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

@Getter
public class MenuManager {

    private static final Logger logger = LoggerFactory.getLogger(MenuManager.class);
    private final MenuItemManager itemManager = new MenuItemManager();
    private final MenuFunctionManager functionManager = new MenuFunctionManager();
    private final MenuRegistry registry = new MenuRegistry();

    public MenuManager() {
        Bukkit.getServer().getPluginManager().registerEvents(new MenuListener(this), PrimaxCore.inst());
    }

    public MenuHolder getMenuHolder(Inventory inventory) {
        if (!(inventory.getHolder() instanceof MenuHolder holder)) {
            return null;
        }
        return holder;
    }

    public CustomMenu getMenu(JavaPlugin plugin, Section section) {
        CustomMenu menu = createMenu(plugin, section);
        if (menu instanceof CoreMenu coreMenu && coreMenu.containsKey()) {
            registry.register(coreMenu.getKey(), coreMenu);
        }
        return menu;
    }

    private CustomMenu createMenu(JavaPlugin plugin, Section section) {
        CustomMenu menu;
        if (section.contains("reference")) {
            menu = new ReferenceMenu(ConfigUtils.parseKey(section, "reference"));
        }else {
            menu = createCoreMenu(plugin, section);
        }

        if (section.contains("functions")) {
            menu.setFunctions(functionManager.getFunctions(plugin, section.getSection("functions")));
        }
        return menu;
    }

    private CoreMenu createCoreMenu(JavaPlugin plugin, Section section) {
        PlaceholderComponent placeholderTitle = new PlaceholderComponent(section.getString("title"));
        int size = getSize(section);
        CoreMenu menu = section.contains("id")
                ? new CoreMenu(new Key(plugin, section.getString("id")), placeholderTitle, size)
                : new CoreMenu(placeholderTitle, size);
        if (section.contains("items")) {
            menu.addMenuItems(itemManager.getMenuItems(plugin, section.getSection("items")));
        }
        if (section.contains("update-interval")) {
            menu.setUpdateInterval(section.getLong("update-interval"));
        }
        Operation openOperation = Operation.getOperation(plugin, section, "open");
        if (openOperation != null) {
            menu.setOpenOperation(openOperation);
        }
        if (section.contains("close-actions")) {
            menu.setCloseAction(PrimaxCore.inst().getActionManager().getAction(plugin, section.getSection("close-actions")));
        }
        return menu;
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

    public List<CustomMenu> load(JavaPlugin plugin, String folder) {
        File newFolder = new File(plugin.getDataFolder() + "/" + folder);
        return getMenus(plugin, ConfigUtils.listFilesDeep(newFolder));
    }

    public CustomMenu getMenu(JavaPlugin plugin, File file) {
        return getMenu(plugin, ConfigUtils.load(file));
    }

    public List<CustomMenu> getMenus(JavaPlugin plugin, Section objectsSection) {
        return objectsSection.getRoutesAsStrings(false).stream()
                .map((route) -> getMenu(plugin, objectsSection.getSection(route)))
                .toList();
    }

    public List<CustomMenu> getMenus(JavaPlugin plugin, List<File> files) {
        return files.stream()
                .map(file -> getMenu(plugin, file))
                .toList();
    }
}
