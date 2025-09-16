package net.primaxstudios.primaxcore.item.property.type;

import net.primaxstudios.primaxcore.config.Config;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxcore.item.property.MetaProperty;
import net.primaxstudios.primaxcore.version.VersionManager;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class EnchantsProperty extends MetaProperty {

    public static final String ID = "enchants";
    private static final Logger logger = LoggerFactory.getLogger(EnchantsProperty.class);

    public EnchantsProperty() {
        super(logger);
    }

    @Override
    public boolean setProperty(@NotNull ItemMeta meta, @NotNull Section section) {
        Map<Enchantment, Integer> enchantments = createEnchantments(section);
        if (enchantments.isEmpty()) {
            Config.warn(logger, section, "Missing or empty '{}' key", ID);
            return false;
        }
        enchantments.forEach((enchant, level) -> meta.addEnchant(enchant, level, true));
        return true;
    }

    public Map<Enchantment, Integer> createEnchantments(Section section) {
        Map<Enchantment, Integer> enchantments = new HashMap<>();
        for (String rawEnchant : section.getStringList(ID)) {
            String[] parts = rawEnchant.split(";");
            if (parts.length != 2) {
                Config.warn(logger, section, "Invalid enchantment format '{}'", rawEnchant);
                continue;
            }

            NamespacedKey key = NamespacedKey.fromString(parts[0]);
            if (key == null) {
                Config.warn(logger, section, "Invalid NamespacedKey '{}' in enchantment '{}'", parts[0], rawEnchant);
                continue;
            }

            int level;
            try {
                level = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                Config.warn(logger, section, "Invalid enchantment level '{}' in enchantment '{}'", parts[1], rawEnchant);
                continue;
            }

            Enchantment enchantment = VersionManager.get().getEnchantment(key);
            if (enchantment == null) {
                Config.warn(logger, section, "Unknown enchantment key '{}'", key);
                continue;
            }

            enchantments.put(enchantment, level);
        }
        return enchantments;
    }
}