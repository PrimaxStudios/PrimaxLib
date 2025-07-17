package net.primaxstudios.primaxcore.utils;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.primaxstudios.primaxcore.PrimaxCore;
import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public final class ConfigUtils {

    private static final Logger logger = LoggerFactory.getLogger(ConfigUtils.class);
    private static final LoaderSettings LOADER_SETTINGS = LoaderSettings.builder().setCreateFileIfAbsent(true).setAutoUpdate(true).build();

    private ConfigUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static YamlDocument load(File file) {
        try {
            return YamlDocument.create(file);
        }catch (IOException e) {
            logger.error("Failed to load config", e);
            throw new RuntimeException();
        }
    }

    public static YamlDocument load(JavaPlugin plugin, String fileName) {
        return load(new File(plugin.getDataFolder(), fileName));
    }

    public static YamlDocument loadDefault(JavaPlugin plugin, String fileName) {
        File file = saveDefault(plugin, fileName);
        InputStream defaults = Objects.requireNonNull(plugin.getResource(fileName));
        try {
            return YamlDocument.create(file, defaults,
                    LOADER_SETTINGS,
                    UpdaterSettings.builder()
                            .setVersioning(new BasicVersioning("config-version"))
                            .build());
        } catch (IOException e) {
            logger.error("Failed to load config", e);
            throw new RuntimeException();
        }
    }

    public static File saveDefault(JavaPlugin plugin, String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            plugin.saveResource(fileName, false);
        }
        return file;
    }

    public static List<File> saveDefaults(JavaPlugin plugin, String folder, String... fileNames) {
        List<File> files = new ArrayList<>();
        for (String fileName : fileNames) {
            File file = saveDefault(plugin, folder + "/" + fileName);
            files.add(file);
        }
        return files;
    }

    public static String getString(JavaPlugin plugin, Section section, String route) {
        String value = section.getString(route);
        if (value == null) {
            return null;
        }
        String localeValue = PrimaxCore.inst().getLocale().getSimpleMessage(CommonUtils.getNamespace(plugin), value);
        return localeValue != null ? localeValue : value;
    }

    public static List<String> getStringList(JavaPlugin plugin, Section section, String route) {
        List<String> list = new ArrayList<>();
        for (String value : section.getStringList(route)) {
            String localeValue = PrimaxCore.inst().getLocale().getSimpleMessage(CommonUtils.getNamespace(plugin), value);
            list.add(localeValue != null ? localeValue : value);
        }
        return list;
    }

    public static <T extends Enum<T>> List<T> parseEnumList(Section section, String route, Class<T> aClass) {
        return section.getStringList(route).stream()
                .map(line -> {
                    try {
                        return Enum.valueOf(aClass, line.toUpperCase(Locale.ROOT));
                    } catch (IllegalArgumentException e) {
                        logger.warn("Invalid enum value '{}' in section '{}' of '{}'", line, section.getName(), section.getRoot().getFile(), e);
                        throw new RuntimeException();
                    }
                })
                .toList();
    }

    public static NamespacedKey parseNamespacedKey(Section section, String route) {
        Object sectionName = section.getName();
        File filePath = section.getRoot().getFile();

        String key = section.getString(route);
        if (key == null) {
            logger.warn("Missing '{}' key in section '{}' of '{}'", route, sectionName, filePath);
            throw new RuntimeException();
        }
        try {
            return NamespacedKey.fromString(key);
        }catch (Exception e) {
            logger.warn("Invalid namespaced key '{}' in section '{}' of '{}'", key, sectionName, filePath, e);
            throw new RuntimeException();
        }
    }

    public static Key parseKey(Section section, String route) {
        Object sectionName = section.getName();
        File filePath = section.getRoot().getFile();

        String key = section.getString(route);
        if (key == null) {
            logger.warn("Missing '{}' key in section '{}' of '{}'", route, sectionName, filePath);
            throw new RuntimeException();
        }
        try {
            return Key.fromString(key);
        }catch (Exception e) {
            logger.warn("Invalid namespaced key '{}' in section '{}' of '{}'", key, sectionName, filePath, e);
            throw new RuntimeException();
        }
    }

    public static PotionEffect parsePotionEffect(Section section) {
        Object sectionName = section.getName();
        File filePath = section.getRoot().getFile();

        PotionEffectType type = RegistryAccess.registryAccess().getRegistry(RegistryKey.MOB_EFFECT).get(parseNamespacedKey(section, "key"));
        if (type == null) {
            logger.warn("Unknown potion effect key '{}' in section '{}' of '{}'", section.getString("key"), sectionName, filePath);
            throw new RuntimeException();
        }

        if (!section.contains("duration")) {
            logger.warn("Missing 'duration' key in section '{}' of '{}'", sectionName, filePath);
            throw new RuntimeException();
        }
        int duration = section.getInt("duration");

        if (!section.contains("amplifier")) {
            logger.warn("Missing 'amplifier' key in section '{}' of '{}'", sectionName, filePath);
            throw new RuntimeException();
        }
        int amplifier = section.getInt("amplifier");

        return new PotionEffect(type, duration, amplifier);
    }

    public static Location parseLocation(Section section) {
        Object sectionName = section.getName();
        File filePath = section.getRoot().getFile();

        String worldName = section.getString("world");
        if (worldName == null) {
            logger.warn("Missing 'world' key in section '{}' of '{}'", sectionName, filePath);
            throw new RuntimeException();
        }
        World world = Bukkit.getWorld(worldName);

        if (!section.contains("x") || !section.contains("y") || !section.contains("z")) {
            logger.warn("Missing 'x', 'y' or 'z' key in section '{}' of '{}'", sectionName, filePath);
            throw new RuntimeException();
        }
        double x = section.getDouble("x");
        double y = section.getDouble("y");
        double z = section.getDouble("z");

        float yaw = section.getFloat("yaw");
        float pitch = section.getFloat("pitch");

        return new Location(world, x, y, z, yaw, pitch);
    }

    public static List<Integer> parseSlots(Section section, String route) {
        Object sectionName = section.getName();
        File filePath = section.getRoot().getFile();

        List<String> rawSlots = section.getStringList(route);
        if (rawSlots == null || rawSlots.isEmpty()) {
            logger.warn("Missing '{}' key in section '{}' of '{}'", route, sectionName, filePath);
            throw new RuntimeException();
        }

        List<Integer> slots = new ArrayList<>();
        for (String rawSlot : rawSlots) {
            slots.addAll(parseSlots(rawSlot, sectionName, filePath));
        }
        return slots;
    }

    private static List<Integer> parseSlots(String rawSlot, Object sectionName, File filePath) {
        List<Integer> slots = new ArrayList<>();
        if (rawSlot.contains("-")) {
            String[] parts = rawSlot.split("-");
            if (parts.length != 2) {
                logger.warn("Invalid slot range format '{}' in section '{}' of '{}'. Expected format: 'start-end'", rawSlot, sectionName, filePath);
                throw new RuntimeException();
            }

            int startingNum;
            int endingNum;
            try {
                startingNum = Integer.parseInt(parts[0]);
                endingNum = Integer.parseInt(parts[1]);
            }catch (NumberFormatException e) {
                logger.warn("Non-numeric value in slot range '{}' in section '{}' of '{}'. Start: '{}', End: '{}'", rawSlot, sectionName, filePath, parts[0], parts[1], e);
                throw new RuntimeException();
            }

            for (int slot = startingNum; slot <= endingNum; slot++) {
                slots.add(slot);
            }
        }else {
            int slot;
            try {
                slot = Integer.parseInt(rawSlot);
            }catch (NumberFormatException e) {
                logger.warn("Invalid numeric slot '{}' in section '{}' of '{}'. Must be a valid integer", rawSlot, sectionName, filePath, e);
                throw new RuntimeException();
            }
            slots.add(slot);
        }
        return slots;
    }

    public static void serialize(Location location, ConfigurationSection section) {
        section.set("world", location.getWorld().getName());
        section.set("x", location.getX());
        section.set("y", location.getY());
        section.set("z", location.getZ());
        section.set("pitch", location.getPitch());
        section.set("yaw", location.getYaw());
    }

    public static Location deserialize(ConfigurationSection section) {
        World world = Bukkit.getWorld(Objects.requireNonNull(section.getString("world")));
        double x = section.getDouble("x");
        double y = section.getDouble("y");
        double z = section.getDouble("z");
        float pitch = section.getInt("pitch");
        float yaw = section.getInt("yaw");
        return new Location(world, x, y, z, pitch, yaw);
    }

    public static Color parseColor(String value) {
        String[] split = value.split(",");
        return Color.fromRGB(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
    }

    public static List<File> listFilesDeep(File folder) {
        List<File> files = new ArrayList<>();
        if (folder != null && folder.isDirectory()) {
            File[] newFiles = folder.listFiles();
            if (newFiles == null) {
                return files;
            }
            for (File file : newFiles) {
                if (file.isFile()) {
                    files.add(file);
                } else if (file.isDirectory()) {
                    files.addAll(listFilesDeep(file));
                }
            }
        }
        return files;
    }
}