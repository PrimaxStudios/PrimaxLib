package net.primaxstudios.primaxcore.items.properties.meta;

import net.primaxstudios.primaxcore.configs.Config;
import net.primaxstudios.primaxcore.items.properties.AdvancedMetaProperty;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrimProperty extends AdvancedMetaProperty<ArmorMeta> {

    public static final String ID = "armor_trim";
    private static final Logger logger = LoggerFactory.getLogger(TrimProperty.class);

    public TrimProperty() {
        super(logger, ArmorMeta.class);
    }

    @Override
    public boolean setProperty(@NotNull ArmorMeta meta, @NotNull Section section) {
        NamespacedKey materialKey = ConfigUtils.parseNamespacedKey(section, "material");
        if (materialKey == null) return false;

        NamespacedKey patternKey = ConfigUtils.parseNamespacedKey(section, "pattern");
        if (patternKey == null) return false;

        TrimMaterial material = RegistryAccess.registryAccess()
                .getRegistry(RegistryKey.TRIM_MATERIAL)
                .get(materialKey);
        TrimPattern pattern = RegistryAccess.registryAccess()
                .getRegistry(RegistryKey.TRIM_PATTERN)
                .get(patternKey);
        if (material == null || pattern == null) {
            Config.warn(logger, section, "Invalid material or pattern '{}' / '{}'", materialKey, patternKey);
            return false;
        }

        ArmorTrim trim = new ArmorTrim(material, pattern);

        meta.setTrim(trim);
        return true;
    }
}