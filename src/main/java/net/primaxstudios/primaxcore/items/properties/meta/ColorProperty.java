package net.primaxstudios.primaxcore.items.properties.meta;

import net.primaxstudios.primaxcore.configs.Config;
import net.primaxstudios.primaxcore.items.properties.MetaProperty;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.inventory.meta.*;
import org.bukkit.plugin.java.JavaPlugin;
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
    public boolean setProperty(@NotNull ItemMeta meta, @NotNull JavaPlugin plugin, @NotNull Section section) {
        Color color = ConfigUtils.parseRGBColor(section, ID);
        if (color == null) {
            return false;
        }

        if (meta instanceof LeatherArmorMeta leatherArmorMeta) {
            leatherArmorMeta.setColor(color);
        }else if (meta instanceof FireworkEffectMeta fireworkEffectMeta) {
            fireworkEffectMeta.setEffect(FireworkEffect.builder().withColor(color).build());
        }else if (meta instanceof PotionMeta potionMeta) {
            potionMeta.setColor(color);
        }else if (meta instanceof MapMeta mapMeta) {
            mapMeta.setColor(color);
        }else {
            Config.warn(logger, section, "Unsupported ItemMeta type for '{}'", ID);
            return false;
        }
        return true;
    }
}