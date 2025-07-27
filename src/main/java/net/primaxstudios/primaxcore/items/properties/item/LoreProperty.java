package net.primaxstudios.primaxcore.items.properties.item;

import net.primaxstudios.primaxcore.configs.Config;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxcore.items.properties.AdvancedMetaProperty;
import net.primaxstudios.primaxcore.utils.ColorUtils;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LoreProperty extends AdvancedMetaProperty<ItemMeta> {

    public static final String ID = "lore";
    private static final Logger logger = LoggerFactory.getLogger(LoreProperty.class);

    public LoreProperty() {
        super(logger, ItemMeta.class);
    }

    @Override
    public void setProperty(@NotNull ItemMeta meta, @NotNull Section section) {
        List<String> lore = section.getStringList(ID);
        if (lore == null || lore.isEmpty()) {
            Config.warn(logger, section, "Missing or empty '{}' key", ID);
            return;
        }
        meta.lore(lore.stream().map(ColorUtils::getComponent).toList());
    }
}