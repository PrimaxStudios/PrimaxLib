package net.primaxstudios.primaxlib.util;

import net.primaxstudios.primaxlib.PrimaxLib;
import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import net.primaxstudios.primaxlib.config.Config;
import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;
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

    public static YamlDocument load(File file) throws IOException {
        return YamlDocument.create(file);
    }

    public static YamlDocument load(JavaPlugin plugin, String fileName) throws IOException {
        return load(new File(plugin.getDataFolder(), fileName));
    }

    public static File saveDefault(JavaPlugin plugin, String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            plugin.saveResource(fileName, false);
        }
        return file;
    }

    public static List<File> saveDefaults(JavaPlugin plugin, String folder, String... filenames) {
        List<File> files = new ArrayList<>();
        for (String fileName : filenames) {
            File file = saveDefault(plugin, folder + "/" + fileName);
            files.add(file);
        }
        return files;
    }

    public static File saveVersionedDefault(JavaPlugin plugin, String fileName) throws IOException {
        File file = new File(plugin.getDataFolder(), fileName);
        InputStream defaults = Objects.requireNonNull(plugin.getResource(fileName));
        YamlDocument.create(file, defaults, LOADER_SETTINGS, UpdaterSettings.builder()
                .setVersioning(new BasicVersioning("config_version"))
                .build());
        return file;
    }

    public static List<File> saveVersionedDefaults(JavaPlugin plugin, String folder, String... filenames) throws IOException {
        List<File> files = new ArrayList<>();
        for (String fileName : filenames) {
            File file = saveVersionedDefault(plugin, folder + "/" + fileName);
            files.add(file);
        }
        return files;
    }

    public static String getString(Section section, String route) {
        String value = section.getString(route);
        if (value == null) {
            return null;
        }
        String localeValue = PrimaxLib.inst().getLocale().getSimpleMessage(value);
        return localeValue != null ? localeValue : value;
    }

    public static List<String> getStringList(Section section, String route) {
        return section.getStringList(route).stream()
                .map(value -> Objects.requireNonNullElse(PrimaxLib.inst().getLocale().getSimpleMessage(value), value))
                .flatMap(line -> Arrays.stream(line.split("\n")))
                .toList();
    }

    public static <T extends Enum<T>> T parseEnum(Section section, String route, Class<T> eClass) {
        String value = section.getString(route);
        if (value == null) {
            Config.warn(logger, section, "'{}' is missing", route);
            return null;
        }

        try {
            return Enum.valueOf(eClass, value.toUpperCase(Locale.ROOT));
        }catch (IllegalArgumentException | NullPointerException ex) {
            Config.warn(logger, section, "Invalid enum value '{}'", value);
            return null;
        }
    }

    public static <T extends Enum<T>> List<T> parseEnumList(Section section, String route, Class<T> aClass) {
        return section.getStringList(route).stream()
                .map(line -> {
                    try {
                        return Enum.valueOf(aClass, line.toUpperCase(Locale.ROOT));
                    } catch (IllegalArgumentException | NullPointerException e) {
                        Config.warn(logger, section, "Invalid enum value '{}'", line);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }

    public static NamespacedKey parseNamespacedKey(Section section, String route) {
        String key = section.getString(route);
        if (key == null) {
            Config.warn(logger, section, "Missing '{}' key", route);
            return null;
        }

        try {
            return NamespacedKey.fromString(key);
        }catch (Exception e) {
            Config.warn(logger, section, "Invalid namespaced key '{}'", key);
            return null;
        }
    }

    public static Location parseLocation(Section section) {
        String worldName = section.getString("world");
        if (worldName == null) {
            Config.warn(logger, section, "Missing 'world' key");
            return null;
        }
        World world = Bukkit.getWorld(worldName);

        if (!section.contains("x") || !section.contains("y") || !section.contains("z")) {
            Config.warn(logger, section, "Missing 'x', 'y' or 'z' key");
            return null;
        }
        double x = section.getDouble("x");
        double y = section.getDouble("y");
        double z = section.getDouble("z");

        float yaw = section.getFloat("yaw");
        float pitch = section.getFloat("pitch");

        return new Location(world, x, y, z, yaw, pitch);
    }

    public static List<Integer> parseSlots(Section section, String route) {
        List<String> rawSlots = section.getStringList(route);
        if (rawSlots == null || rawSlots.isEmpty()) {
            Config.warn(logger, section, "Missing '{}' key");
            return null;
        }

        List<Integer> slots = new ArrayList<>();
        for (String rawSlot : rawSlots) {
            slots.addAll(parseSlots(rawSlot, section));
        }
        return slots;
    }

    private static List<Integer> parseSlots(String rawSlot, Section section) {
        List<Integer> slots = new ArrayList<>();
        if (rawSlot.contains("-")) {
            String[] parts = rawSlot.split("-");
            if (parts.length != 2) {
                Config.warn(logger, section, "Invalid slot range format '{}'", rawSlot);
                return new ArrayList<>();
            }

            int startingNum;
            int endingNum;
            try {
                startingNum = Integer.parseInt(parts[0]);
                endingNum = Integer.parseInt(parts[1]);
            }catch (NumberFormatException e) {
                Config.warn(logger, section, "Non-numeric value in slot range '{}'", rawSlot);
                return new ArrayList<>();
            }

            for (int slot = startingNum; slot <= endingNum; slot++) {
                slots.add(slot);
            }
        }else {
            int slot;
            try {
                slot = Integer.parseInt(rawSlot);
            }catch (NumberFormatException e) {
                Config.warn(logger, section, "Invalid numeric slot '{}'", rawSlot);
                return new ArrayList<>();
            }
            slots.add(slot);
        }
        return slots;
    }

    public static Color parseRGBColor(Section section, String route) {
        String value = section.getString(route);
        if (value == null) {
            Config.warn(logger, section, "Missing key '{}'", route);
            return null;
        }

        String[] split = value.split(",");
        if (split.length != 3) {
            Config.warn(logger, section, "Invalid rgb format '{}'. Valid Format: 'r,g,b'", value);
            return null;
        }

        try {
            return Color.fromRGB(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        } catch (NumberFormatException e) {
            Config.warn(logger, section, "Invalid rgb value '{}'. Values must be integers.", value);
            return null;
        }
    }

    public static void serialize(Location location, Section section) {
        section.set("world", location.getWorld().getName());
        section.set("x", location.getX());
        section.set("y", location.getY());
        section.set("z", location.getZ());
        section.set("pitch", location.getPitch());
        section.set("yaw", location.getYaw());
    }

    public static Location deserialize(Section section) {
        World world = Bukkit.getWorld(Objects.requireNonNull(section.getString("world")));
        double x = section.getDouble("x");
        double y = section.getDouble("y");
        double z = section.getDouble("z");
        float pitch = section.getInt("pitch");
        float yaw = section.getInt("yaw");
        return new Location(world, x, y, z, pitch, yaw);
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

    public static File getFile(JavaPlugin plugin, String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            plugin.saveResource(fileName, false);
        }
        return file;
    }
}