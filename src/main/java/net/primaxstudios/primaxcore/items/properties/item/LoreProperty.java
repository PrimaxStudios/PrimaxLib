package net.primaxstudios.primaxcore.items.properties.item;

import net.primaxstudios.primaxcore.configs.Config;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxcore.items.properties.MetaProperty;
import net.primaxstudios.primaxcore.utils.ColorUtils;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
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
    public boolean setProperty(@NotNull ItemMeta meta, @NotNull JavaPlugin plugin, @NotNull Section section) {
        List<String> lore = ConfigUtils.getStringList(plugin, section, ID);
        if (lore.isEmpty()) {
            Config.warn(logger, section, "Missing or empty '{}' key", ID);
            return false;
        }

        meta.lore(lore.stream().map(ColorUtils::getComponent).toList());
        return true;
    }
}