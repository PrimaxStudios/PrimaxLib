package net.primaxstudios.primaxcore.items.properties.meta;

import net.primaxstudios.primaxcore.configs.Config;
import net.primaxstudios.primaxcore.items.properties.AdvancedMetaProperty;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
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
        patterns.forEach(meta::addPattern);
        return true;
    }

    public List<Pattern> createPatterns(Section section) {
        List<Pattern> patterns = new ArrayList<>();

        for (String route : section.getRoutesAsStrings(false)) {
            Section patternSection = section.getSection(route);
            if (patternSection == null) {
                Config.warn(logger, section, "Pattern section '{}' is missing", route);
                continue;
            }

            DyeColor color = ConfigUtils.parseEnum(section, "color", DyeColor.class);
            if (color == null) continue;

            NamespacedKey key = ConfigUtils.parseNamespacedKey(section, "type");
            if (key == null) continue;

            PatternType type = RegistryAccess.registryAccess()
                    .getRegistry(RegistryKey.BANNER_PATTERN)
                    .get(key);
            if (type == null) {
                Config.warn(logger, section, "No PatternType found for key '{}'", key);
                continue;
            }

            patterns.add(new Pattern(color, type));
        }

        return patterns;
    }
}