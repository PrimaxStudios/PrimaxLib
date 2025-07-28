package net.primaxstudios.primaxcore.items.properties.item.persistentdata;

import net.primaxstudios.primaxcore.configs.Config;
import net.primaxstudios.primaxcore.items.properties.MetaProperty;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PersistentDataProperty extends MetaProperty {

    public static final String ID = "persistent_data";
    private static final Logger logger = LoggerFactory.getLogger(PersistentDataProperty.class);

    public PersistentDataProperty() {
        super(logger);
    }

    @Override
    public boolean setProperty(@NotNull ItemMeta meta, @NotNull Section section) {
        PersistentDataContainer container = meta.getPersistentDataContainer();
        for (PersistentObject object : createObjects(section)) {
            object.setData(container, section);
        }
        return true;
    }

    private List<PersistentObject> createObjects(Section section) {
        List<PersistentObject> objects = new ArrayList<>();

        for (String route : section.getRoutesAsStrings(false)) {
            Section objectSection = section.getSection(route);
            if (objectSection == null) {
                Config.warn(logger, section, "Missing section at route '{}'", route);
                continue;
            }

            String typeStr = objectSection.getString("type");
            String value = objectSection.getString("value");
            if (typeStr == null || value == null) {
                Config.warn(logger, section, "Missing 'key', 'type', or 'value' in persistent-data route '{}'", route);
                continue;
            }

            NamespacedKey key;
            try {
                key = ConfigUtils.parseNamespacedKey(objectSection, "key");
            } catch (IllegalArgumentException e) {
                Config.warn(logger, section, "Invalid NamespacedKey format at route '{}'", route);
                continue;
            }

            PersistentDataType<?, ?> type = getType(typeStr);
            if (type == null) {
                Config.warn(logger, section, "Unsupported PersistentDataType '{}' at route '{}'", typeStr, route);
                continue;
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
}