    package net.primaxstudios.primaxcore.items.properties.item.persistentdata;

    import dev.dejvokep.boostedyaml.block.implementation.Section;
    import lombok.Getter;
    import net.primaxstudios.primaxcore.configs.Config;
    import org.bukkit.NamespacedKey;
    import org.bukkit.persistence.PersistentDataContainer;
    import org.bukkit.persistence.PersistentDataType;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;

    @Getter
    public class PersistentObject {

        private static final Logger logger = LoggerFactory.getLogger(PersistentObject.class);
        private final NamespacedKey key;
        private final PersistentDataType<?, ?> type;
        private final String value;

        public PersistentObject(NamespacedKey key, PersistentDataType<?, ?> type, String value) {
            this.key = key;
            this.type = type;
            this.value = value;
        }

        public void setData(PersistentDataContainer container, Section section) {
            try {
                if (type.equals(PersistentDataType.DOUBLE)) {
                    container.set(key, PersistentDataType.DOUBLE, Double.parseDouble(value));
                } else if (type.equals(PersistentDataType.BOOLEAN)) {
                    container.set(key, PersistentDataType.BOOLEAN, Boolean.parseBoolean(value));
                } else if (type.equals(PersistentDataType.STRING)) {
                    container.set(key, PersistentDataType.STRING, value);
                } else if (type.equals(PersistentDataType.INTEGER)) {
                    container.set(key, PersistentDataType.INTEGER, Integer.parseInt(value));
                } else if (type.equals(PersistentDataType.LONG)) {
                    container.set(key, PersistentDataType.LONG, Long.parseLong(value));
                }
            } catch (NumberFormatException e) {
                Config.warn(logger, section, "Invalid number format for key '{}': value='{}'", key, value);
            }
        }
    }