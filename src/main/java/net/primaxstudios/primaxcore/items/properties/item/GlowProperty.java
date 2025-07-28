package net.primaxstudios.primaxcore.items.properties.item;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxcore.items.properties.MetaProperty;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlowProperty extends MetaProperty {

    public static final String ID = "glow";
    private static final Logger logger = LoggerFactory.getLogger(GlowProperty.class);

    public GlowProperty() {
        super(logger);
    }

    @Override
    public boolean setProperty(@NotNull ItemMeta meta, @NotNull Section section) {
        boolean glow = section.getBoolean(ID);
        meta.setEnchantmentGlintOverride(glow);
        return true;
    }
}