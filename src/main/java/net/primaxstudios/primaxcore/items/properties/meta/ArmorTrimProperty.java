package net.primaxstudios.primaxcore.items.properties.meta;

import net.primaxstudios.primaxcore.configs.Config;
import net.primaxstudios.primaxcore.items.properties.AdvancedMetaProperty;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxcore.versions.VersionManager;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArmorTrimProperty extends AdvancedMetaProperty<ArmorMeta> {

    public static final String ID = "armor_trim";
    private static final Logger logger = LoggerFactory.getLogger(ArmorTrimProperty.class);

    public ArmorTrimProperty() {
        super(logger, ArmorMeta.class);
    }

    @Override
    public boolean setProperty(@NotNull ArmorMeta meta, @NotNull Section section) {
        String string = section.getString(ID);
        String[] split = string.split(";");

        NamespacedKey materialKey;
        try {
            materialKey = NamespacedKey.fromString(split[0]);
        } catch (Exception e) {
            Config.warn(logger, section, "Invalid material namespaced key '{}'", split[0]);
            return false;
        }

        NamespacedKey patternKey;
        try {
            patternKey = NamespacedKey.fromString(split[1]);
        } catch (Exception e) {
            Config.warn(logger, section, "Invalid pattern namespaced key '{}'", split[1]);
            return false;
        }

        TrimMaterial material = VersionManager.get().getTrimMaterial(materialKey);
        if (material == null) {
            Config.warn(logger, section, "Invalid material '{}'", materialKey);
            return false;
        }

        TrimPattern pattern = VersionManager.get().getTrimPattern(patternKey);
        if (pattern == null) {
            Config.warn(logger, section, "Invalid pattern '{}'", materialKey, patternKey);
            return false;
        }

        ArmorTrim trim = new ArmorTrim(material, pattern);

        meta.setTrim(trim);
        return true;
    }
}