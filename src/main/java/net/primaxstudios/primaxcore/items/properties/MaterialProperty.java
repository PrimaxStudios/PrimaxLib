package net.primaxstudios.primaxcore.items.properties;

import com.destroystokyo.paper.profile.PlayerProfile;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxcore.configs.Config;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionType;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class MaterialProperty {

    private static final String ID = "material";
    private static final Logger logger = LoggerFactory.getLogger(MaterialProperty.class);

    public ItemStack getItem(@NotNull Section section) {
        String rawMaterial = section.getString(ID);
        if (rawMaterial == null) {
            return defaultMaterial();
        }

        if (rawMaterial.toUpperCase(Locale.ROOT).equals("WATER_BOTTLE")) {
            return createWaterBottle(section);
        }
        if (rawMaterial.contains("head-")) {
            String playerName = rawMaterial.replace("HEAD-", "");

            UUID uuid = Bukkit.getPlayerUniqueId(playerName);
            if (uuid == null) {
                Config.warn(logger, section, "Unknown player '{}'", playerName);
                return defaultMaterial();
            }

            return createPlayerHead(Bukkit.getOfflinePlayer(Objects.requireNonNull(uuid)), section);
        }
        if (rawMaterial.contains("texture-")) {
            return createCustomHead(rawMaterial.replace("texture-", ""), section);
        }
        return createMaterialItem(section);
    }

    private ItemStack defaultMaterial() {
        return new ItemStack(Material.STONE);
    }

    private ItemStack createMaterialItem(Section section) {
        Material material = ConfigUtils.parseEnum(section, ID, Material.class);
        if (material == null) {
            return defaultMaterial();
        }
        return new ItemStack(material);
    }

    private ItemStack createWaterBottle(Section section) {
        ItemStack item = new ItemStack(Material.POTION);
        if (!(item.getItemMeta() instanceof PotionMeta meta)) {
            Config.warn(logger, section, "ItemMeta is not PotionMeta");
            return defaultMaterial();
        }
        meta.setBasePotionType(PotionType.WATER);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createPlayerHead(OfflinePlayer player, Section section) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        if (!(item.getItemMeta() instanceof SkullMeta meta)) {
            Config.warn(logger, section, "ItemMeta is not PotionMeta");
            return defaultMaterial();
        }
        meta.setOwningPlayer(player);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createCustomHead(String headTexture, Section section) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        if (!(item.getItemMeta() instanceof SkullMeta meta)) {
            Config.warn(logger, section, "ItemMeta is not PotionMeta");
            return defaultMaterial();
        }

        try {
            PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID(), null);
            PlayerTextures textures = profile.getTextures();
            textures.setSkin(new URI("https://textures.minecraft.net/texture/" + headTexture).toURL());
            profile.setTextures(textures);
            meta.setPlayerProfile(profile);
            item.setItemMeta(meta);
        } catch (MalformedURLException | URISyntaxException e) {
            Config.warn(logger, section, "Invalid texture URL '{}'", headTexture);
            return defaultMaterial();
        }
        return item;
    }
}