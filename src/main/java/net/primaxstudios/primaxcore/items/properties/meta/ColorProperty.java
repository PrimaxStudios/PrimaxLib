package net.primaxstudios.primaxcore.items.properties.meta;

import net.primaxstudios.primaxcore.items.properties.ItemProperty;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ColorProperty implements ItemProperty {

    public static final String ID = "color";
    private static final Logger logger = LoggerFactory.getLogger(ColorProperty.class);

    @Override
    public void setProperty(@NotNull ItemStack item, @NotNull Section section) {
        Object sectionName = section.getName();
        File file = section.getRoot().getFile();

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            logger.warn("ItemMeta is null for item '{}' in section '{}' of '{}'", item.getType(), sectionName, file);
            throw new IllegalArgumentException("ItemMeta is null.");
        }

        String colorString = section.getString(ID);
        if (colorString == null || colorString.isEmpty()) {
            logger.warn("Missing or empty '{}' key in section '{}' of '{}'", ID, sectionName, file);
            throw new IllegalArgumentException("Color value is missing or empty.");
        }

        Color color;
        try {
            color = ConfigUtils.parseColor(colorString);
        } catch (Exception e) {
            logger.error("Failed to parse color '{}' in section '{}' of '{}'", colorString, sectionName, file, e);
            throw new IllegalArgumentException("Invalid color format: " + colorString, e);
        }

        boolean success = false;

        switch (meta) {
            case LeatherArmorMeta leatherArmorMeta -> {
                leatherArmorMeta.setColor(color);
                success = true;
            }
            case FireworkEffectMeta fireworkEffectMeta -> {
                FireworkEffect effect = FireworkEffect.builder().withColor(color).build();
                fireworkEffectMeta.setEffect(effect);
                success = true;
            }
            case PotionMeta potionMeta -> {
                potionMeta.setColor(color);
                success = true;
            }
            case MapMeta mapMeta -> {
                mapMeta.setColor(color);
                success = true;
            }
            default -> {
            }
        }

        if (!success) {
            logger.warn("ItemMeta of type '{}' does not support color in section '{}' of '{}'",
                    meta.getClass().getSimpleName(), sectionName, file);
            throw new IllegalArgumentException("Unsupported ItemMeta type for color.");
        }

        item.setItemMeta(meta);
    }
}