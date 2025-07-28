package net.primaxstudios.primaxcore.items.properties.meta;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxcore.configs.Config;
import net.primaxstudios.primaxcore.items.properties.AdvancedMetaProperty;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import org.bukkit.entity.Axolotl;
import org.bukkit.inventory.meta.AxolotlBucketMeta;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AxolotlProperty extends AdvancedMetaProperty<AxolotlBucketMeta> {

    public static final String ID = "axolotl_variant";
    private static final Logger logger = LoggerFactory.getLogger(AxolotlProperty.class);

    public AxolotlProperty() {
        super(logger, AxolotlBucketMeta.class);
    }

    @Override
    public boolean setProperty(@NotNull AxolotlBucketMeta meta, @NotNull Section section) {
        String variantStr = section.getString(ID);
        if (variantStr == null) {
            Config.warn(logger, section, "Missing '{}' key");
            return false;
        }

        Axolotl.Variant variant = ConfigUtils.parseEnum(section, ID, Axolotl.Variant.class);
        if (variant == null) {
            Config.warn(logger, section, "Invalid axolotl variant '{}'", variantStr);
            return false;
        }

        meta.setVariant(variant);
        return true;
    }
}