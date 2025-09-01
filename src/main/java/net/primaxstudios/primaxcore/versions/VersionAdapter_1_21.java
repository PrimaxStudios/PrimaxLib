package net.primaxstudios.primaxcore.versions;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.NamespacedKey;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.potion.PotionEffectType;

public class VersionAdapter_1_21 implements VersionAdapter {

    @Override
    public Enchantment getEnchantment(NamespacedKey key) {
        return RegistryAccess.registryAccess()
                .getRegistry(RegistryKey.ENCHANTMENT)
                .get(key);
    }

    @Override
    public TrimMaterial getTrimMaterial(NamespacedKey key) {
        return RegistryAccess.registryAccess()
                .getRegistry(RegistryKey.TRIM_MATERIAL)
                .get(key);
    }

    @Override
    public TrimPattern getTrimPattern(NamespacedKey key) {
        return RegistryAccess.registryAccess()
                .getRegistry(RegistryKey.TRIM_PATTERN)
                .get(key);
    }

    @Override
    public PotionEffectType getPotionEffectType(NamespacedKey key) {
        return RegistryAccess.registryAccess()
                .getRegistry(RegistryKey.MOB_EFFECT)
                .get(key);
    }

    @Override
    public PatternType getPatternType(NamespacedKey key) {
        return RegistryAccess.registryAccess()
                .getRegistry(RegistryKey.BANNER_PATTERN)
                .get(key);
    }
}
