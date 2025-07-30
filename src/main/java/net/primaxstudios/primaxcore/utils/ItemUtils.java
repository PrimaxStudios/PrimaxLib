package net.primaxstudios.primaxcore.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.function.Consumer;

public final class ItemUtils {

    private ItemUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static void formatLore(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        List<Component> lore = meta.lore();
        if (lore == null) return;

        List<Component> formattedLore = lore.stream()
                .map(ColorUtils::color)
                .flatMap(s -> Arrays.stream(s.split("\\n")))
                .map(ColorUtils::getComponent)
                .toList();

        meta.lore(formattedLore);
        item.setItemMeta(meta);
    }

    public static String serialize(ItemStack item) {
        if (item == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString(item.serializeAsBytes());
    }

    public static ItemStack deserialize(String rawItem) {
        if (rawItem == null) {
            return null;
        }
        return ItemStack.deserializeBytes(Base64.getDecoder().decode(rawItem));
    }

    public static void give(Player player, ItemStack item, boolean overflow) {
        give(player, item, item.getAmount(), overflow);
    }

    public static void give(Player player, ItemStack item, int amount, boolean overflow) {
        performAction(item, amount, (actionItem) -> handleGive(player, actionItem, overflow));
    }

    public static void drop(Location location, ItemStack item) {
        drop(location, item, item.getAmount());
    }

    public static void drop(Location location, ItemStack item, int amount) {
        performAction(item, amount, (actionItem) -> handleDrop(location, actionItem));
    }

    private static void performAction(ItemStack item, int amount, Consumer<ItemStack> action) {
        ItemStack cloned = item.clone();
        cloned.setAmount(1);
        int maxStackSize = item.getMaxStackSize();
        int total = amount;
        while (total > 0) {
            int toGive = Math.min(total, maxStackSize);
            ItemStack toGiveItem = cloned.clone();
            toGiveItem.setAmount(toGive);
            action.accept(toGiveItem);
            total -= toGive;
        }
    }

    private static void handleGive(Player player, ItemStack item, boolean overflow) {
        HashMap<Integer, ItemStack> leftOvers = player.getInventory().addItem(item);
        if (leftOvers.isEmpty() || !overflow) {
            return;
        }
        handleOverflow(player, leftOvers);
    }

    private static void handleOverflow(Player player, HashMap<Integer, ItemStack> leftOvers) {
        for (ItemStack leftOver : leftOvers.values()) {
            handleDrop(player.getLocation(), leftOver);
        }
    }

    private static void handleDrop(Location location, ItemStack item) {
        location.getWorld().dropItemNaturally(location, item);
    }
}
