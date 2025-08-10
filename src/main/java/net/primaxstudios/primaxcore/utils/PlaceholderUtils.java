package net.primaxstudios.primaxcore.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

public final class PlaceholderUtils {

    private PlaceholderUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    private static final DecimalFormat FORMATTER = new DecimalFormat("#,###.#");

    public static String getNumber(String placeholder, double number) {
        if (placeholder.contains("_raw")) {
            return String.valueOf(number);
        }
        if (placeholder.contains("_formatted")) {
            return NumberUtils.getMoney(number);
        }
        return FORMATTER.format(number);
    }
    private static final Pattern PATTERN = Pattern.compile("%[^%]+%");

    public static boolean hasPlaceholder(String string) {
        return PATTERN.matcher(string).find();
    }

    public static boolean hasPlaceholder(List<String> list) {
        for (String string : list) {
            if (hasPlaceholder(string)) {
                return true;
            }
        }
        return false;
    }

    public static String setPlaceholder(String message) {
        if (Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
            return message;
        }
        return PlaceholderAPI.setPlaceholders(Bukkit.getOfflinePlayer(UUID.randomUUID()), message);
    }

    public static String setPlaceholder(String message, OfflinePlayer player) {
        if (Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
            return message;
        }
        if (player == null) {
            return setPlaceholder(message);
        }
        return PlaceholderAPI.setPlaceholders(player, message);
    }

    public static List<String> setPlaceholder(List<String> messages) {
        if (Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
            return messages;
        }
        return PlaceholderAPI.setPlaceholders(Bukkit.getOfflinePlayer(UUID.randomUUID()), messages);
    }

    public static List<String> setPlaceholder(List<String> messages, OfflinePlayer player) {
        if (Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
            return messages;
        }
        if (player == null) {
            return setPlaceholder(messages);
        }
        return PlaceholderAPI.setPlaceholders(player, messages);
    }
}
