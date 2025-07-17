package net.primaxstudios.primaxcore.items.properties;

import com.destroystokyo.paper.profile.PlayerProfile;
import dev.dejvokep.boostedyaml.block.implementation.Section;
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

import java.io.File;
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
        Object sectionName = section.getName();
        File filePath = section.getRoot().getFile();
        String rawMaterial = section.getString(ID);
        if (rawMaterial == null) {
            logger.warn("Missing '{}' key in section '{}' of '{}'", ID, sectionName, filePath);
            throw new IllegalArgumentException();
        }

        if (rawMaterial.toUpperCase(Locale.ROOT).equals("WATER_BOTTLE")) {
            return createWaterBottle();
        }
        if (rawMaterial.contains("head-")) {
            String playerName = rawMaterial.replace("HEAD-", "");

            UUID uuid = Bukkit.getPlayerUniqueId(playerName);
            if (uuid == null) {
                logger.warn("Unknown player '{}' in section '{}' of '{}'", playerName, sectionName, filePath);
                throw new IllegalArgumentException();
            }

            return createPlayerHead(Bukkit.getOfflinePlayer(Objects.requireNonNull(uuid)));
        }
        if (rawMaterial.contains("texture-")) {
            return createCustomHead(rawMaterial.replace("texture-", ""), sectionName, filePath);
        }
        return createMaterialItem(section);
    }

    private ItemStack createMaterialItem(Section section) {
        Object sectionName = section.getName();
        File filePath = section.getRoot().getFile();
        Material material = section.getEnum(ID, Material.class);
        if (material == null) {
            logger.warn("Invalid material enum value '{}' in section '{}' of '{}'", section.getString(ID), sectionName, filePath);
            throw new RuntimeException();
        }
        return new ItemStack(material);
    }

    private ItemStack createWaterBottle() {
        ItemStack item = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.setBasePotionType(PotionType.WATER);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createPlayerHead(OfflinePlayer player) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwningPlayer(player);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createCustomHead(String headTexture, Object sectionName, File filePath) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        try {
            PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID(), null);
            PlayerTextures textures = profile.getTextures();
            textures.setSkin(new URI("https://textures.minecraft.net/texture/" + headTexture).toURL());
            profile.setTextures(textures);
            meta.setPlayerProfile(profile);
            item.setItemMeta(meta);
        } catch (MalformedURLException | URISyntaxException e) {
            logger.error("Invalid texture URL '{}' in section '{}' of '{}'", headTexture, sectionName, filePath, e);
            throw new IllegalArgumentException();
        }
        return item;
    }
}