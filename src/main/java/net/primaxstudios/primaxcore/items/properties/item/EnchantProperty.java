package net.primaxstudios.primaxcore.items.properties.item;

import net.primaxstudios.primaxcore.items.properties.ItemProperty;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class EnchantProperty implements ItemProperty {

    public static final String ID = "enchants";
    private static final Logger logger = LoggerFactory.getLogger(EnchantProperty.class);

    private Map<Enchantment, Integer> createEnchantments(Section section) {
        Map<Enchantment, Integer> enchantments = new HashMap<>();
        File file = section.getRoot().getFile();
        Object sectionName = section.getName();

        for (String rawEnchant : section.getStringList(ID)) {
            try {
                String[] parts = rawEnchant.split(";");
                if (parts.length != 2) {
                    logger.warn("Invalid enchantment format '{}' in section '{}' of '{}'. Expected format: namespace:key;level",
                            rawEnchant, sectionName, file);
                    throw new RuntimeException("Invalid enchantment format: " + rawEnchant);
                }

                NamespacedKey key = NamespacedKey.fromString(parts[0]);
                if (key == null) {
                    logger.warn("Invalid NamespacedKey '{}' in enchantment '{}' in section '{}' of '{}'",
                            parts[0], rawEnchant, sectionName, file);
                    throw new RuntimeException("Invalid NamespacedKey: " + parts[0]);
                }

                int level;
                try {
                    level = Integer.parseInt(parts[1]);
                } catch (NumberFormatException e) {
                    logger.warn("Invalid enchantment level '{}' in enchantment '{}' in section '{}' of '{}'",
                            parts[1], rawEnchant, sectionName, file);
                    throw new RuntimeException("Invalid enchantment level: " + parts[1], e);
                }

                Enchantment enchantment = RegistryAccess.registryAccess()
                        .getRegistry(RegistryKey.ENCHANTMENT)
                        .get(key);
                if (enchantment == null) {
                    logger.warn("Unknown enchantment key '{}' in section '{}' of '{}'", key, sectionName, file);
                    throw new RuntimeException("Unknown enchantment key: " + key);
                }

                enchantments.put(enchantment, level);
            } catch (RuntimeException e) {
                logger.warn("Error parsing enchantment '{}' in section '{}' of '{}'", rawEnchant, sectionName, file, e);
                throw e;
            }
        }

        return enchantments;
    }

    @Override
    public void setProperty(@NotNull ItemStack item, @NotNull Section section) {
        File file = section.getRoot().getFile();
        Object sectionName = section.getName();

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            logger.warn("ItemMeta is null for item '{}' in section '{}' of '{}'", item.getType(), sectionName, file);
            throw new RuntimeException("ItemMeta is null");
        }

        Map<Enchantment, Integer> enchantments = createEnchantments(section);
        enchantments.forEach((enchant, level) -> meta.addEnchant(enchant, level, true));
        item.setItemMeta(meta);
    }
}