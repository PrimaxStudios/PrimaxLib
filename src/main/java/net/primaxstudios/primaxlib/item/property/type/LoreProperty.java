package net.primaxstudios.primaxlib.item.property.type;

import net.primaxstudios.primaxlib.configuration.Config;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxlib.item.property.MetaProperty;
import net.primaxstudios.primaxlib.util.ColorUtils;
import net.primaxstudios.primaxlib.util.ConfigUtils;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LoreProperty extends MetaProperty {

    public static final String ID = "lore";
    private static final Logger logger = LoggerFactory.getLogger(LoreProperty.class);

    public LoreProperty() {
        super(logger);
    }

    @Override
    public boolean setProperty(@NotNull ItemMeta meta, @NotNull Section section) {
        List<String> lore = ConfigUtils.getStringList(section, ID);
        if (lore.isEmpty()) {
            Config.warn(logger, section, "Missing or empty '{}' key", ID);
            return false;
        }

        meta.lore(lore.stream().map(ColorUtils::getComponent).toList());
        return true;
    }
}