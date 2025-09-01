package net.primaxstudios.primaxcore.versions;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.potion.PotionEffectType;

@SuppressWarnings("deprecation")
public class VersionAdapter_1_20 implements VersionAdapter {

    @Override
    public Enchantment getEnchantment(NamespacedKey key) {
        return Registry.ENCHANTMENT.get(key);
    }

    @Override
    public TrimMaterial getTrimMaterial(NamespacedKey key) {
        return Registry.TRIM_MATERIAL.get(key);
    }

    @Override
    public TrimPattern getTrimPattern(NamespacedKey key) {
        return Registry.TRIM_PATTERN.get(key);
    }

    @Override
    public PotionEffectType getPotionEffectType(NamespacedKey key) {
        return Registry.POTION_EFFECT_TYPE.get(key);
    }

    @Override
    public PatternType getPatternType(NamespacedKey key) {
        return Registry.BANNER_PATTERN.get(key);
    }
}
