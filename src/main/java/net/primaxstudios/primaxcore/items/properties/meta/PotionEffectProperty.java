package net.primaxstudios.primaxcore.items.properties.meta;

import net.primaxstudios.primaxcore.items.properties.ItemProperty;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PotionEffectProperty implements ItemProperty {

    public static final String ID = "potion-effects";
    private static final Logger logger = LoggerFactory.getLogger(PotionEffectProperty.class);

    @Override
    public void setProperty(@NotNull ItemStack item, @NotNull Section section) {
        Object sectionName = section.getName();
        File file = section.getRoot().getFile();

        if (!(item.getItemMeta() instanceof PotionMeta meta)) {
            logger.warn("Item '{}' in section '{}' of '{}' does not support PotionMeta. Cannot apply potion effects.",
                    item.getType(), sectionName, file);
            throw new IllegalArgumentException("PotionMeta not supported on item.");
        }

        try {
            List<PotionEffect> effects = createEffects(section, sectionName, file);
            effects.forEach(effect -> meta.addCustomEffect(effect, true));
            item.setItemMeta(meta);
        } catch (Exception e) {
            logger.error("Failed to apply potion effects in section '{}' of '{}': {}", sectionName, file, e.getMessage(), e);
            throw e;
        }
    }

    private List<PotionEffect> createEffects(Section section, Object sectionName, File filePath) {
        List<PotionEffect> effects = new ArrayList<>();
        for (String route : section.getRoutesAsStrings(false)) {
            try {
                PotionEffect effect = ConfigUtils.parsePotionEffect(section.getSection(route));
                effects.add(effect);
            } catch (Exception e) {
                logger.warn("Invalid potion effect at '{}.{}' in '{}': {}", sectionName, route, filePath, e.getMessage());
                throw new IllegalArgumentException("Failed to parse potion effect at route: " + route, e);
            }
        }
        return effects;
    }
}