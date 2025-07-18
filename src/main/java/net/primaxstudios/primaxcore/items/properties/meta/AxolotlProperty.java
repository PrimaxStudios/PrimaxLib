package net.primaxstudios.primaxcore.items.properties.meta;

import net.primaxstudios.primaxcore.items.properties.ItemProperty;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.entity.Axolotl;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.AxolotlBucketMeta;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class AxolotlProperty implements ItemProperty {

    public static final String ID = "axolotl_variant";
    private static final Logger logger = LoggerFactory.getLogger(AxolotlProperty.class);

    @Override
    public void setProperty(@NotNull ItemStack item, @NotNull Section section) {
        Object sectionName = section.getName();
        File file = section.getRoot().getFile();

        if (!(item.getItemMeta() instanceof AxolotlBucketMeta meta)) {
            logger.warn("Item '{}' in section '{}' of '{}' does not support AxolotlBucketMeta.",
                    item.getType(), sectionName, file);
            throw new IllegalArgumentException("ItemMeta is not AxolotlBucketMeta.");
        }

        String variantStr = section.getString(ID);
        if (variantStr == null) {
            logger.warn("Missing '{}' key in section '{}' of '{}'", ID, sectionName, file);
            throw new IllegalArgumentException("Missing axolotl variant.");
        }

        Axolotl.Variant variant = section.getEnum(ID, Axolotl.Variant.class);
        if (variant == null) {
            logger.warn("Invalid axolotl variant '{}' in section '{}' of '{}'", variantStr, sectionName, file);
            throw new IllegalArgumentException("Invalid axolotl variant: " + variantStr);
        }

        meta.setVariant(variant);
        item.setItemMeta(meta);
    }
}