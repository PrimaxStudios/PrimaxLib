package net.primaxstudios.primaxcore.items.properties.item;

import net.primaxstudios.primaxcore.items.properties.ItemProperty;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import net.primaxstudios.primaxcore.utils.ColorUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

@Getter
public class LoreProperty implements ItemProperty {

    public static final String ID = "lore";
    private static final Logger logger = LoggerFactory.getLogger(LoreProperty.class);

    @Override
    public void setProperty(@NotNull ItemStack item, @NotNull Section section) {
        Object sectionName = section.getName();
        File file = section.getRoot().getFile();

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            logger.warn("ItemMeta is null for item: {} in section '{}' of '{}'", item.getType(), sectionName, file);
            throw new RuntimeException("ItemMeta is null");
        }

        List<String> lore = section.getStringList(ID);
        if (lore == null || lore.isEmpty()) {
            logger.warn("Missing or empty '{}' key in section '{}' of '{}'", ID, sectionName, file);
            throw new IllegalArgumentException("Missing or empty lore in section.");
        }

        meta.lore(lore.stream().map(ColorUtils::getComponent).toList());
        item.setItemMeta(meta);
    }
}