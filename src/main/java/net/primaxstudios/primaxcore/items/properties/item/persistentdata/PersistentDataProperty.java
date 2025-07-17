package net.primaxstudios.primaxcore.items.properties.item.persistentdata;

import net.primaxstudios.primaxcore.items.properties.ItemProperty;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PersistentDataProperty implements ItemProperty {

    public static final String ID = "persistent-data";
    private static final Logger logger = LoggerFactory.getLogger(PersistentDataProperty.class);

    private List<PersistentObject> createObjects(Section section) {
        List<PersistentObject> objects = new ArrayList<>();
        File file = section.getRoot().getFile();
        Object sectionName = section.getName();

        for (String route : section.getRoutesAsStrings(false)) {
            Section objectSection = section.getSection(route);
            if (objectSection == null) {
                logger.warn("Missing section at route '{}' in section '{}' of '{}'", route, sectionName, file);
                throw new RuntimeException();
            }

            String typeStr = objectSection.getString("type");
            String value = objectSection.getString("value");
            if (typeStr == null || value == null) {
                logger.warn("Missing 'key', 'type', or 'value' in persistent-data route '{}' in section '{}' of '{}'", route, sectionName, file);
                throw new RuntimeException();
            }

            NamespacedKey key;
            try {
                key = ConfigUtils.parseNamespacedKey(objectSection, "key");
            } catch (IllegalArgumentException e) {
                logger.warn("Invalid NamespacedKey format at route '{}' in section '{}' of '{}'", route, sectionName, file, e);
                throw new RuntimeException();
            }

            PersistentDataType<?, ?> type = getType(typeStr);
            if (type == null) {
                logger.warn("Unsupported PersistentDataType '{}' at route '{}' in section '{}' of '{}'", typeStr, route, sectionName, file);
                throw new RuntimeException();
            }

            objects.add(new PersistentObject(key, type, value));
        }
        return objects;
    }

    private PersistentDataType<?, ?> getType(String type) {
        return switch (type.toLowerCase(Locale.ROOT)) {
            case "string" -> PersistentDataType.STRING;
            case "boolean" -> PersistentDataType.BOOLEAN;
            case "integer" -> PersistentDataType.INTEGER;
            case "long" -> PersistentDataType.LONG;
            case "double" -> PersistentDataType.DOUBLE;
            default -> null;
        };
    }

    @Override
    public void setProperty(@NotNull ItemStack item, @NotNull Section section) {
        File file = section.getRoot().getFile();
        Object sectionName = section.getName();

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            logger.warn("Item meta is null for item '{}' in section '{}' of '{}'", item.getType(), sectionName, file);
            throw new RuntimeException("ItemMeta is null");
        }

        PersistentDataContainer container = meta.getPersistentDataContainer();
        for (PersistentObject object : createObjects(section)) {
            object.setData(container);
        }

        item.setItemMeta(meta);
    }
}