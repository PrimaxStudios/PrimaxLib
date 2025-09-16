package net.primaxstudios.primaxlib.item.property.type;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxlib.config.Config;
import net.primaxstudios.primaxlib.item.property.MetaProperty;
import net.primaxstudios.primaxlib.util.ColorUtils;
import net.primaxstudios.primaxlib.util.ConfigUtils;
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
    public boolean setProperty(@NotNull ItemMeta meta, @NotNull Section section) {
        String displayName = ConfigUtils.getString(section, ID);
        if (displayName == null) {
            Config.warn(logger, section, "Missing '{}' key", ID);
            return false;
        }

        meta.displayName(ColorUtils.getComponent(displayName));
        return true;
    }
}