package net.primaxstudios.primaxlib.factory;

import com.destroystokyo.paper.profile.PlayerProfile;
import net.primaxstudios.primaxlib.PrimaxLib;
import net.primaxstudios.primaxlib.configuration.Config;
import net.primaxstudios.primaxlib.item.CoreItem;
import net.primaxstudios.primaxlib.item.CustomItem;
import net.primaxstudios.primaxlib.item.DataItem;
import net.primaxstudios.primaxlib.item.property.ItemPropertyType;
import net.primaxstudios.primaxlib.util.ConfigUtils;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.profile.PlayerTextures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

public final class CustomItemFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomItemFactory.class);
    private static final ItemStack DEFAULT_ITEM = new ItemStack(Material.STONE);

    private CustomItemFactory() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Creates a CustomItem from a config Section.
     */
    public static CustomItem fromSection(Section section) {
        if (section.contains("data")) {
            return new DataItem(section.getString("data"));
        }
        return createCoreItem(section);
    }

    /**
     * Creates a CoreItem from a Section.
     */
    private static CoreItem createCoreItem(Section section) {
        ItemStack itemStack = createItemStack(section);
        if (itemStack == null) {
            LOGGER.warn("Failed to create ItemStack from section '{}' in '{}'",
                    section.getName(), section.getRoot().getFile());
            return null;
        }

        CoreItem coreItem = section.contains("id")
                ? new CoreItem(section.getString("id"), itemStack)
                : new CoreItem(itemStack);

        // Apply properties from section
        for (ItemPropertyType type : ItemPropertyType.fromSection(section)) {
            coreItem.setProperty(type.getProperty(), section);
        }

        return coreItem;
    }

    /**
     * Creates an ItemStack from a Section.
     */
    public static ItemStack createItemStack(Section section) {
        String rawMaterial = section.getString("material");
        if (rawMaterial == null) {
            return DEFAULT_ITEM.clone();
        }

        if (rawMaterial.startsWith("head-")) {
            return createPlayerHead(rawMaterial.substring(5), section);
        }

        if (rawMaterial.startsWith("texture-")) {
            return createTexturedHead(rawMaterial.substring(8), section);
        }

        return createFromMaterial(section);
    }

    private static ItemStack createFromMaterial(Section section) {
        Material material = ConfigUtils.parseEnum(section, "material", Material.class);
        return material != null ? new ItemStack(material) : DEFAULT_ITEM.clone();
    }

    private static ItemStack createPlayerHead(String playerName, Section section) {
        UUID uuid = Bukkit.getPlayerUniqueId(playerName);
        if (uuid == null) {
            Config.warn(LOGGER, section, "Unknown player '{}'", playerName);
            return DEFAULT_ITEM.clone();
        }
        return createPlayerHead(Bukkit.getOfflinePlayer(uuid), section);
    }

    private static ItemStack createPlayerHead(OfflinePlayer player, Section section) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        if (!(item.getItemMeta() instanceof SkullMeta meta)) {
            Config.warn(LOGGER, section, "ItemMeta is not SkullMeta");
            return DEFAULT_ITEM.clone();
        }
        meta.setOwningPlayer(player);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack createTexturedHead(String headTexture, Section section) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        if (!(item.getItemMeta() instanceof SkullMeta meta)) {
            Config.warn(LOGGER, section, "ItemMeta is not SkullMeta");
            return DEFAULT_ITEM.clone();
        }

        try {
            PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID(), null);
            PlayerTextures textures = profile.getTextures();
            textures.setSkin(new URI("https://textures.minecraft.net/texture/" + headTexture).toURL());
            profile.setTextures(textures);
            meta.setPlayerProfile(profile);
            item.setItemMeta(meta);
        } catch (MalformedURLException | URISyntaxException e) {
            Config.warn(LOGGER, section, "Invalid texture URL '{}'", headTexture);
            return DEFAULT_ITEM.clone();
        }

        return item;
    }

    /**
     * Loads all CustomItems from a folder inside the plugin's data folder.
     */
    public static List<CustomItem> loadAll(JavaPlugin plugin, String folder) throws IOException {
        File targetFolder = new File(plugin.getDataFolder(), folder);
        return getItemsFromFiles(ConfigUtils.listFilesDeep(targetFolder));
    }

    /**
     * Loads a single CustomItem from a file.
     */
    public static CustomItem fromFile(File file) throws IOException {
        return fromSection(ConfigUtils.load(file));
    }

    /**
     * Loads multiple CustomItems from a list of Sections.
     */
    public static List<CustomItem> fromSectionList(Section objectsSection) {
        return objectsSection.getRoutesAsStrings(false).stream()
                .map(route -> fromSection(objectsSection.getSection(route)))
                .collect(Collectors.toList());
    }

    /**
     * Loads multiple CustomItems from a list of files.
     */
    public static List<CustomItem> getItemsFromFiles(List<File> files) throws IOException {
        List<CustomItem> items = new ArrayList<>();
        for (File file : files) {
            items.add(fromFile(file));
        }
        return items;
    }

    /**
     * Retrieves the persistent key of an ItemStack.
     */
    public static String getKey(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return null;
        return item.getItemMeta().getPersistentDataContainer()
                .get(PrimaxLib.getIdentifierKey(), org.bukkit.persistence.PersistentDataType.STRING);
    }
}
