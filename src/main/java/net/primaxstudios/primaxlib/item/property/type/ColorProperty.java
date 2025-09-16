package net.primaxstudios.primaxlib.item.property.type;

import net.primaxstudios.primaxlib.configuration.Config;
import net.primaxstudios.primaxlib.item.property.MetaProperty;
import net.primaxstudios.primaxlib.util.ConfigUtils;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.inventory.meta.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColorProperty extends MetaProperty {

    public static final String ID = "color";
    private static final Logger logger = LoggerFactory.getLogger(ColorProperty.class);

    public ColorProperty() {
        super(logger);
    }

    @Override
    public boolean setProperty(@NotNull ItemMeta meta, @NotNull Section section) {
        Color color = ConfigUtils.parseRGBColor(section, ID);
        if (color == null) return false;

        switch (meta) {
            case LeatherArmorMeta leatherArmorMeta -> leatherArmorMeta.setColor(color);
            case FireworkEffectMeta fireworkEffectMeta -> fireworkEffectMeta.setEffect(FireworkEffect.builder().withColor(color).build());
            case PotionMeta potionMeta -> potionMeta.setColor(color);
            case MapMeta mapMeta -> mapMeta.setColor(color);
            default -> {
                Config.warn(logger, section, "Unsupported ItemMeta type for '{}'", ID);
                return false;
            }
        }
        return true;
    }
}