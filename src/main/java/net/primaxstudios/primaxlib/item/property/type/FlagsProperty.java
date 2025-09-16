package net.primaxstudios.primaxlib.item.property.type;

import net.primaxstudios.primaxlib.config.Config;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxlib.item.property.MetaProperty;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class FlagsProperty extends MetaProperty {

    public static final String ID = "flags";
    private static final Logger logger = LoggerFactory.getLogger(FlagsProperty.class);

    public FlagsProperty() {
        super(logger);
    }

    @Override
    public boolean setProperty(@NotNull ItemMeta meta, @NotNull Section section) {
        Set<ItemFlag> flags = createFlags(section);
        flags.forEach(meta::addItemFlags);
        return true;
    }

    public Set<ItemFlag> createFlags(Section section) {
        Set<ItemFlag> flags = new HashSet<>();

        for (String rawFlag : section.getStringList(ID)) {
            ItemFlag flag;
            try {
                flag = ItemFlag.valueOf(rawFlag.toUpperCase());
            } catch (IllegalArgumentException e) {
                Config.warn(logger, section, "Invalid item flag '{}'", rawFlag);
                continue;
            }
            flags.add(flag);
        }
        return flags;
    }
}