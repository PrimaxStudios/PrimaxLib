package net.primaxstudios.primaxlib.item.property.type;

import net.primaxstudios.primaxlib.configuration.Config;
import net.primaxstudios.primaxlib.item.property.SpecificMetaProperty;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxlib.version.VersionManager;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PotionEffectsProperty extends SpecificMetaProperty<PotionMeta> {

    public static final String ID = "potion_effects";
    private static final Logger logger = LoggerFactory.getLogger(PotionEffectsProperty.class);

    public PotionEffectsProperty() {
        super(logger, PotionMeta.class);
    }

    @Override
    public boolean setProperty(@NotNull PotionMeta meta, @NotNull Section section) {
        List<PotionEffect> effects = createEffects(section);
        if (effects.isEmpty()) {
            Config.warn(logger, section, "Missing or empty '{}' key", ID);
            return false;
        }

        effects.forEach(effect -> meta.addCustomEffect(effect, true));
        return true;
    }

    public List<PotionEffect> createEffects(Section section) {
        List<PotionEffect> effects = new ArrayList<>();
        for (String string : section.getStringList(ID)) {
            String[] split = string.split(";");
            if (split.length != 3) {
                Config.warn(logger, section, "Unknown format '{}'", string);
                continue;
            }

            NamespacedKey key;
            try {
                key = NamespacedKey.fromString(split[0]);
            } catch (Exception e) {
                Config.warn(logger, section, "Invalid namespaced key '{}'", split[0]);
                continue;
            }

            PotionEffectType type = VersionManager.get().getPotionEffectType(key);
            if (type == null) {
                Config.warn(logger, section, "Unknown potion effect key '{}'", key);
                continue;
            }

            int duration;
            try {
                duration = Integer.parseInt(split[1]);

            } catch (NumberFormatException e) {
                Config.warn(logger, section, "Missing 'duration' key");
                continue;
            }

            int amplifier;
            try {
                amplifier = Integer.parseInt(split[2]);
            } catch (NumberFormatException e) {
                Config.warn(logger, section, "Missing 'amplifier' key");
                continue;
            }

            PotionEffect effect = new PotionEffect(type, duration, amplifier);
            effects.add(effect);
        }

        return effects;
    }
}