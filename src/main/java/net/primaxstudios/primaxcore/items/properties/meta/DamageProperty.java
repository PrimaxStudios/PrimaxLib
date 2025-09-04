package net.primaxstudios.primaxcore.items.properties.meta;

import net.primaxstudios.primaxcore.configs.Config;
import net.primaxstudios.primaxcore.items.properties.AdvancedMetaProperty;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DamageProperty extends AdvancedMetaProperty<Damageable> {

    public static final String ID = "damage";
    private static final Logger logger = LoggerFactory.getLogger(DamageProperty.class);

    public DamageProperty() {
        super(logger, Damageable.class);
    }

    @Override
    public boolean setProperty(@NotNull Damageable meta, @NotNull JavaPlugin plugin, @NotNull Section section) {
        int damage = section.getInt(ID, -1);
        if (damage < 0) {
            Config.warn(logger, section, "Invalid or missing '{}' value '{}'. Damage must be 0 or greater.", ID, damage);
            return false;
        }

        meta.setDamage(damage);
        return true;
    }
}