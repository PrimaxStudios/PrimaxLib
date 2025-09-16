package net.primaxstudios.primaxcore.managers;

import net.primaxstudios.primaxcore.PrimaxCore;
import net.primaxstudios.primaxcore.items.CoreItem;
import net.primaxstudios.primaxcore.items.CustomItem;
import net.primaxstudios.primaxcore.items.DataItem;
import net.primaxstudios.primaxcore.items.properties.ItemProperty;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ItemManager {

    private static final Logger logger = LoggerFactory.getLogger(ItemManager.class);
    private final ItemPropertyManager propertyManager = new ItemPropertyManager();

    public String getKey(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return null;
        }
        PersistentDataContainer container = meta.getPersistentDataContainer();
        return container.get(PrimaxCore.IDENTIFIER_KEY, PersistentDataType.STRING);
    }

    public CustomItem getItem(Section section) {
        if (section.contains("data")) {
            return new DataItem(section.getString("data"));
        }
        return createCoreItem(section);
    }

    private CoreItem createCoreItem(Section section) {
        ItemStack item = propertyManager.getRegistry().getMaterialProperty().getItem(section);
        if (item == null) {
            logger.warn("Failed to create ItemStack from section '{}' of '{}'", section.getName(), section.getRoot().getFile());
            return null;
        }
        CoreItem coreItem = section.contains("id")
                ? new CoreItem(section.getString("id"), item)
                : new CoreItem(item);

        for (ItemProperty property : propertyManager.getProperties(section)) {
            coreItem.setProperty(property, section);
        }
        return coreItem;
    }

    public List<CustomItem> load(JavaPlugin plugin, String folder) throws IOException {
        File newFolder = new File(plugin.getDataFolder() + "/" + folder);
        return getItems(ConfigUtils.listFilesDeep(newFolder));
    }

    public CustomItem getItem(File file) throws IOException {
        return getItem(ConfigUtils.load(file));
    }

    public List<CustomItem> getItems(Section objectsSection) {
        return objectsSection.getRoutesAsStrings(false).stream()
                .map((route) -> getItem(objectsSection.getSection(route)))
                .toList();
    }

    public List<CustomItem> getItems(List<File> files) throws IOException {
        List<CustomItem> items = new ArrayList<>();
        for (File file : files) {
            items.add(getItem(file));
        }
        return items;
    }
}