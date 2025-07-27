package net.primaxstudios.primaxcore.items.properties.item;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxcore.configs.Config;
import net.primaxstudios.primaxcore.items.properties.MetaProperty;
import net.primaxstudios.primaxcore.utils.ColorUtils;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DisplayNameProperty extends MetaProperty {

    public static final String ID = "display_name";
    private static final Logger logger = LoggerFactory.getLogger(DisplayNameProperty.class);

    public DisplayNameProperty() {
        super(logger);
    }

    @Override
    public void setProperty(@NotNull ItemMeta meta, @NotNull Section section) {
        String displayName = section.getString(ID);
        if (displayName == null) {
            Config.warn(logger, section, "Missing '{}' key", ID);
            return;
        }

        meta.displayName(ColorUtils.getComponent(displayName));
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
}