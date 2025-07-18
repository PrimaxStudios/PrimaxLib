package net.primaxstudios.primaxcore.items.properties.meta;

import net.primaxstudios.primaxcore.items.properties.ItemProperty;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class TrimProperty implements ItemProperty {

    public static final String ID = "armor_trim";
    private static final Logger logger = LoggerFactory.getLogger(TrimProperty.class);

    @Override
    public void setProperty(@NotNull ItemStack item, @NotNull Section section) {
        Object sectionName = section.getName();
        File file = section.getRoot().getFile();

        if (!(item.getItemMeta() instanceof ArmorMeta meta)) {
            logger.warn("Item '{}' in section '{}' of '{}' does not support ArmorMeta. Cannot apply armor trim.",
                    item.getType(), sectionName, file);
            throw new IllegalArgumentException("ArmorMeta not supported on item.");
        }

        try {
            ArmorTrim trim = createTrim(section, sectionName, file);
            meta.setTrim(trim);
            item.setItemMeta(meta);
        } catch (Exception e) {
            logger.error("Failed to apply ArmorTrim in section '{}' of '{}': {}", sectionName, file, e.getMessage(), e);
            throw e;
        }
    }

    private ArmorTrim createTrim(Section section, Object sectionName, File filePath) {
        String materialKey = section.getString("material");
        String patternKey = section.getString("pattern");

        if (materialKey == null || patternKey == null) {
            logger.warn("Missing 'material' or 'pattern' keys in section '{}' of '{}'", sectionName, filePath);
            throw new IllegalArgumentException("Missing material or pattern keys.");
        }

        TrimMaterial material = RegistryAccess.registryAccess()
                .getRegistry(RegistryKey.TRIM_MATERIAL)
                .get(NamespacedKey.minecraft(materialKey));
        TrimPattern pattern = RegistryAccess.registryAccess()
                .getRegistry(RegistryKey.TRIM_PATTERN)
                .get(NamespacedKey.minecraft(patternKey));

        if (material == null || pattern == null) {
            logger.warn("Invalid material or pattern '{}' / '{}' in section '{}' of '{}'",
                    materialKey, patternKey, sectionName, filePath);
            throw new IllegalArgumentException("Invalid trim material or pattern.");
        }

        return new ArmorTrim(material, pattern);
    }
}