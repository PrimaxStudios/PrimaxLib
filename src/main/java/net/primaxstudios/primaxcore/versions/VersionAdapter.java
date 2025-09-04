package net.primaxstudios.primaxcore.versions;

import org.bukkit.NamespacedKey;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public interface VersionAdapter {

    Enchantment getEnchantment(NamespacedKey key);

    TrimMaterial getTrimMaterial(NamespacedKey key);

    TrimPattern getTrimPattern(NamespacedKey key);

    PotionEffectType getPotionEffectType(NamespacedKey key);

    PatternType getPatternType(NamespacedKey key);

    PotionType getPotionType(NamespacedKey key);

    EntityType getEntityType(NamespacedKey key);
}
