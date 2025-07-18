package net.primaxstudios.primaxcore.items.properties.item;

import net.primaxstudios.primaxcore.items.properties.ItemProperty;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class CustomModelDataProperty implements ItemProperty {

    public static final String ID = "custom_model_data";
    private static final Logger logger = LoggerFactory.getLogger(CustomModelDataProperty.class);

    @Override
    public void setProperty(@NotNull ItemStack item, @NotNull Section section) {
        File file = section.getRoot().getFile();
        Object sectionName = section.getName();

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            logger.warn("ItemMeta is null for item '{}' in section '{}' of '{}'", item.getType(), sectionName, file);
            throw new RuntimeException("ItemMeta is null");
        }

        int customModelData = section.getInt(ID, -1);
        if (customModelData < 0) {
            logger.warn("Invalid or missing '{}' value in section '{}' of '{}' for item '{}'", ID, sectionName, file, item.getType());
            throw new RuntimeException("Invalid custom model data value");
        }

        meta.setCustomModelData(customModelData);
        item.setItemMeta(meta);
    }
}