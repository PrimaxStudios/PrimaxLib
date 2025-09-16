package net.primaxstudios.primaxcore.items.properties.meta;

import net.primaxstudios.primaxcore.configs.Config;
import net.primaxstudios.primaxcore.items.properties.AdvancedMetaProperty;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxcore.versions.VersionManager;
import org.bukkit.DyeColor;
import org.bukkit.NamespacedKey;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.meta.BannerMeta;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class BannerPatternProperty extends AdvancedMetaProperty<BannerMeta> {

    public static final String ID = "banner_patterns";
    private static final Logger logger = LoggerFactory.getLogger(BannerPatternProperty.class);

    public BannerPatternProperty() {
        super(logger, BannerMeta.class);
    }

    @Override
    public boolean setProperty(@NotNull BannerMeta meta, @NotNull Section section) {
        List<Pattern> patterns = createPatterns(section);
        if (patterns.isEmpty()) {
            Config.warn(logger, section, "Missing or empty '{}' key", ID);
            return false;
        }

        patterns.forEach(meta::addPattern);
        return true;
    }

    public List<Pattern> createPatterns(Section section) {
        List<Pattern> patterns = new ArrayList<>();
        for (String string : section.getStringList(ID)) {
            String[] split = string.split(";");
            if (split.length != 2) {
                Config.warn(logger, section, "Unknown format '{}'", string);
                continue;
            }

            DyeColor dyeColor;
            try {
                dyeColor = DyeColor.valueOf(split[0]);
            } catch (IllegalArgumentException e) {
                Config.warn(logger, section, "Unknown dye color '{}'", split[0]);
                continue;
            }

            NamespacedKey key;
            try {
                key = NamespacedKey.fromString(split[0]);
            } catch (Exception e) {
                Config.warn(logger, section, "Invalid namespaced key '{}'", split[0]);
                continue;
            }

            PatternType patternType = VersionManager.get().getPatternType(key);
            if (patternType == null) {
                Config.warn(logger, section, "Unknown pattern type key '{}'", key);
                continue;
            }

            Pattern pattern = new Pattern(dyeColor, patternType);
            patterns.add(pattern);
        }
        return patterns;
    }
}