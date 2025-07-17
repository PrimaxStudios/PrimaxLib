package net.primaxstudios.primaxcore.items.properties.meta;

import net.primaxstudios.primaxcore.items.properties.ItemProperty;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class DurabilityProperty implements ItemProperty {

    public static final String ID = "durability";
    private static final Logger logger = LoggerFactory.getLogger(DurabilityProperty.class);

    @Override
    public void setProperty(@NotNull ItemStack item, @NotNull Section section) {
        Object sectionName = section.getName();
        File file = section.getRoot().getFile();

        if (!(item.getItemMeta() instanceof Damageable meta)) {
            logger.warn("Item '{}' in section '{}' of '{}' does not support Damageable meta.",
                    item.getType(), sectionName, file);
            throw new IllegalArgumentException("ItemMeta is not Damageable.");
        }

        int durability = section.getInt(ID, -1);
        if (durability < 0) {
            logger.warn("Invalid or missing '{}' value '{}' in section '{}' of '{}'",
                    ID, section.getString(ID), sectionName, file);
            throw new IllegalArgumentException("Durability value must be 0 or greater.");
        }

        meta.setDamage(durability);
        item.setItemMeta(meta);
    }
}