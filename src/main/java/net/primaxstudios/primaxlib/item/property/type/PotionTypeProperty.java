package net.primaxstudios.primaxlib.item.property.type;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxlib.configuration.Config;
import net.primaxstudios.primaxlib.item.property.SpecificMetaProperty;
import net.primaxstudios.primaxlib.util.ConfigUtils;
import net.primaxstudios.primaxlib.version.VersionManager;
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
