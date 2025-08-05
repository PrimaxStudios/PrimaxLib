package net.primaxstudios.primaxcore.items.properties;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import net.primaxstudios.primaxcore.configs.Config;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

@Getter
public abstract class AdvancedMetaProperty<T extends ItemMeta> implements ItemProperty {

    private final Logger logger;
    private final Class<T> metaClass;

    public AdvancedMetaProperty(Logger logger, Class<T> metaClass) {
        this.logger = logger;
        this.metaClass = metaClass;
    }

    public abstract boolean setProperty(@NotNull T meta, @NotNull Section section);

    @Override
    public boolean setProperty(@NotNull ItemStack item, @NotNull Section section) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) {
            Config.warn(logger, section, "ItemMeta is null for item '{}'", item.getType());
            return false;
        }

        T meta;
        try {
            meta = metaClass.cast(itemMeta);
        } catch (ClassCastException e) {
            Config.warn(logger, section, "ItemMeta is not {}", metaClass.getName());
            return false;
        }

        boolean value = setProperty(meta, section);
        item.setItemMeta(meta);
        return value;
    }
}
