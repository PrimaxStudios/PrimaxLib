package net.primaxstudios.primaxcore.item.property.type;

import net.primaxstudios.primaxcore.config.Config;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxcore.item.property.MetaProperty;
import net.primaxstudios.primaxcore.util.ColorUtils;
import net.primaxstudios.primaxcore.util.ConfigUtils;
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