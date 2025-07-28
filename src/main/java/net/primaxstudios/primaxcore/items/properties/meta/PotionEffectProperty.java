package net.primaxstudios.primaxcore.items.properties.meta;

import net.primaxstudios.primaxcore.items.properties.AdvancedMetaProperty;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PotionEffectProperty extends AdvancedMetaProperty<PotionMeta> {

    public static final String ID = "potion_effects";
    private static final Logger logger = LoggerFactory.getLogger(PotionEffectProperty.class);

    public PotionEffectProperty() {
        super(logger, PotionMeta.class);
    }

    @Override
    public boolean setProperty(@NotNull PotionMeta meta, @NotNull Section section) {
        List<PotionEffect> effects = createEffects(section);
        effects.forEach(effect -> meta.addCustomEffect(effect, true));
        return true;
    }

    public List<PotionEffect> createEffects(Section section) {
        List<PotionEffect> effects = new ArrayList<>();
        for (String route : section.getRoutesAsStrings(false)) {
            PotionEffect effect = ConfigUtils.parsePotionEffect(section.getSection(route));
            if (effect == null) continue;

            effects.add(effect);
        }
        return effects;
    }
}