package net.primaxstudios.primaxcore.items.properties.meta;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxcore.items.properties.AdvancedMetaProperty;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PotionTypeProperty extends AdvancedMetaProperty<PotionMeta> {

    public static final String ID = "potion_type";
    private static final Logger logger = LoggerFactory.getLogger(PotionTypeProperty.class);

    public PotionTypeProperty() {
        super(logger, PotionMeta.class);
    }

    @Override
    public boolean setProperty(@NotNull PotionMeta meta, @NotNull JavaPlugin plugin, @NotNull Section section) {
        PotionType type = ConfigUtils.parseEnum(section, ID, PotionType.class);
        if (type == null) return false;

        meta.setBasePotionType(type);
        return true;
    }
}
