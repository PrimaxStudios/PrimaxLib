package net.primaxstudios.primaxcore.item.property.type;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxcore.config.Config;
import net.primaxstudios.primaxcore.item.property.MetaProperty;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomModelDataProperty extends MetaProperty {

    public static final String ID = "custom_model_data";
    private static final Logger logger = LoggerFactory.getLogger(CustomModelDataProperty.class);

    public CustomModelDataProperty() {
        super(logger);
    }

    @Override
    public boolean setProperty(@NotNull ItemMeta meta, @NotNull Section section) {
        int customModelData = section.getInt(ID, -1);
        if (customModelData < 0) {
            Config.warn(logger, section, "Invalid or missing '{}' value", ID);
            return false;
        }

        meta.setCustomModelData(customModelData);
        return true;
    }
}