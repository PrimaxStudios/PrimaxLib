package net.primaxstudios.primaxcore.item.property;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import net.primaxstudios.primaxcore.config.Config;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

@Getter
public abstract class MetaProperty implements ItemProperty {

    private final Logger logger;

    public MetaProperty(Logger logger) {
        this.logger = logger;
    }

    public abstract boolean setProperty(@NotNull ItemMeta meta, @NotNull Section section);

    @Override
    public boolean setProperty(@NotNull ItemStack item, @NotNull Section section) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            Config.warn(logger, section, "ItemMeta is null for item '{}'", item.getType());
            return false;
        }

        setProperty(meta, section);
        item.setItemMeta(meta);
        return true;
    }
}
