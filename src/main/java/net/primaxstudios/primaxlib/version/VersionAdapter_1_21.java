package net.primaxstudios.primaxlib.version;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class VersionAdapter_1_21 implements VersionAdapter {

    @Override
    public Enchantment getEnchantment(NamespacedKey key) {
        return getByRegistry(RegistryKey.ENCHANTMENT, key);
    }

    @Override
    public TrimMaterial getTrimMaterial(NamespacedKey key) {
        return getByRegistry(RegistryKey.TRIM_MATERIAL, key);
    }

    @Override
    public TrimPattern getTrimPattern(NamespacedKey key) {
        return getByRegistry(RegistryKey.TRIM_PATTERN, key);
    }

    @Override
    public PotionEffectType getPotionEffectType(NamespacedKey key) {
        return getByRegistry(RegistryKey.MOB_EFFECT, key);
    }

    @Override
    public PatternType getPatternType(NamespacedKey key) {
        return getByRegistry(RegistryKey.BANNER_PATTERN, key);
    }

    @Override
    public PotionType getPotionType(NamespacedKey key) {
        return getByRegistry(RegistryKey.POTION, key);
    }

    @Override
    public EntityType getEntityType(NamespacedKey key) {
        return getByRegistry(RegistryKey.ENTITY_TYPE, key);
    }

    @Override
    public Sound getSound(NamespacedKey key) {
        return getByRegistry(RegistryKey.SOUND_EVENT, key);
    }

    private <T extends Keyed> T getByRegistry(RegistryKey<T> registryKey, NamespacedKey key) {
        return RegistryAccess.registryAccess()
                .getRegistry(registryKey)
                .get(key);
    }
}
