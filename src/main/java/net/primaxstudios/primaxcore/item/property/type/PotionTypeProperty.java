package net.primaxstudios.primaxcore.item.property.type;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxcore.config.Config;
import net.primaxstudios.primaxcore.item.property.SpecificMetaProperty;
import net.primaxstudios.primaxcore.util.ConfigUtils;
import net.primaxstudios.primaxcore.version.VersionManager;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PotionTypeProperty extends SpecificMetaProperty<PotionMeta> {

    public static final String ID = "potion_type";
    private static final Logger logger = LoggerFactory.getLogger(PotionTypeProperty.class);

    public PotionTypeProperty() {
        super(logger, PotionMeta.class);
    }

    @Override
    public boolean setProperty(@NotNull PotionMeta meta, @NotNull Section section) {
        NamespacedKey key = ConfigUtils.parseNamespacedKey(section, ID);
        if (key == null) return false;

        PotionType type = VersionManager.get().getPotionType(key);
        if (type == null) {
            Config.warn(logger, section, "Invalid potion type key '{}'", key);
            return false;
        }

        meta.setBasePotionType(type);
        return true;
    }
}
