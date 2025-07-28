package net.primaxstudios.primaxcore.items.properties.meta;

import net.primaxstudios.primaxcore.configs.Config;
import net.primaxstudios.primaxcore.items.properties.AdvancedMetaProperty;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.inventory.meta.Damageable;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DurabilityProperty extends AdvancedMetaProperty<Damageable> {

    public static final String ID = "durability";
    private static final Logger logger = LoggerFactory.getLogger(DurabilityProperty.class);

    public DurabilityProperty() {
        super(logger, Damageable.class);
    }

    @Override
    public boolean setProperty(@NotNull Damageable meta, @NotNull Section section) {
        int durability = section.getInt(ID, -1);
        if (durability < 0) {
            Config.warn(logger, section, "Invalid or missing '{}' value '{}'. Durability must be 0 or greater.", ID, durability);
            return false;
        }

        meta.setDamage(durability);
        return true;
    }
}