package net.primaxstudios.primaxcore.items.properties.meta;

import net.primaxstudios.primaxcore.items.properties.ItemProperty;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.DyeColor;
import org.bukkit.NamespacedKey;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BannerPatternProperty implements ItemProperty {

    public static final String ID = "banner_patterns";
    private static final Logger logger = LoggerFactory.getLogger(BannerPatternProperty.class);

    private List<Pattern> createPatterns(Section section, Object sectionName, File file) {
        List<Pattern> patterns = new ArrayList<>();

        for (String route : section.getRoutesAsStrings(false)) {
            Section patternSection = section.getSection(route);
            if (patternSection == null) {
                logger.warn("Pattern section '{}' is null or missing in section '{}' of '{}'", route, sectionName, file);
                throw new IllegalArgumentException("Pattern section missing: " + route);
            }

            String colorStr = patternSection.getString("color");

            if (colorStr == null) {
                logger.warn("Missing 'color' or 'type' in pattern '{}' of section '{}' in '{}'", route, sectionName, file);
                throw new IllegalArgumentException("Missing color or type in pattern: " + route);
            }

            DyeColor color;
            try {
                color = DyeColor.valueOf(colorStr.toUpperCase());
            } catch (IllegalArgumentException ex) {
                logger.warn("Invalid dye color '{}' in pattern '{}' of section '{}' in '{}'", colorStr, route, sectionName, file, ex);
                throw new IllegalArgumentException("Invalid dye color: " + colorStr, ex);
            }

            NamespacedKey typeKey = ConfigUtils.parseNamespacedKey(section, "type");
            if (typeKey == null) {
                logger.warn("Invalid NamespacedKey in pattern '{}' of section '{}' in '{}'", route, sectionName, file);
                throw new IllegalArgumentException("Invalid NamespacedKey");
            }

            PatternType type = RegistryAccess.registryAccess()
                    .getRegistry(RegistryKey.BANNER_PATTERN)
                    .get(typeKey);
            if (type == null) {
                logger.warn("No PatternType found for key '{}' in pattern '{}' of section '{}' in '{}'", typeKey, route, sectionName, file);
                throw new IllegalArgumentException("PatternType not found: " + typeKey);
            }

            patterns.add(new Pattern(color, type));
        }

        return patterns;
    }

    @Override
    public void setProperty(@NotNull ItemStack item, @NotNull Section section) {
        Object sectionName = section.getName();
        File file = section.getRoot().getFile();

        if (!(item.getItemMeta() instanceof BannerMeta meta)) {
            logger.warn("Item '{}' in section '{}' of '{}' does not support BannerMeta", item.getType(), sectionName, file);
            throw new IllegalArgumentException("ItemMeta is not BannerMeta");
        }

        List<Pattern> patterns = createPatterns(section, sectionName, file);
        if (patterns.isEmpty()) {
            logger.warn("No valid banner patterns to apply for item '{}' in section '{}' of '{}'", item.getType(), sectionName, file);
            throw new IllegalArgumentException("No banner patterns found.");
        }

        patterns.forEach(meta::addPattern);
        item.setItemMeta(meta);
    }
}