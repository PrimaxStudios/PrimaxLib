package net.primaxstudios.primaxcore.items.properties.item;

import net.primaxstudios.primaxcore.items.properties.ItemProperty;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class FlagProperty implements ItemProperty {

    public static final String ID = "flags";
    private static final Logger logger = LoggerFactory.getLogger(FlagProperty.class);

    private Set<ItemFlag> createFlags(Section section) {
        Set<ItemFlag> flags = new HashSet<>();
        File file = section.getRoot().getFile();
        Object sectionName = section.getName();

        for (String rawFlag : section.getStringList(ID)) {
            ItemFlag flag;
            try {
                flag = ItemFlag.valueOf(rawFlag.toUpperCase());
            } catch (IllegalArgumentException e) {
                logger.warn("Invalid item flag '{}' in section '{}' of '{}'", rawFlag, sectionName, file, e);
                throw new RuntimeException("Invalid item flag: " + rawFlag, e);
            }
            flags.add(flag);
        }
        return flags;
    }

    @Override
    public void setProperty(@NotNull ItemStack item, @NotNull Section section) {
        File file = section.getRoot().getFile();
        Object sectionName = section.getName();

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            logger.warn("ItemMeta is null for item '{}' in section '{}' of '{}'", item.getType(), sectionName, file);
            throw new RuntimeException("ItemMeta is null");
        }

        Set<ItemFlag> flags = createFlags(section);
        flags.forEach(meta::addItemFlags);
        item.setItemMeta(meta);
    }
}