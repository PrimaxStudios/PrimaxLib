package net.primaxstudios.primaxcore.util;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.kyori.adventure.text.Component;
import net.primaxstudios.primaxcore.menu.scale.InventoryMenuScale;
import net.primaxstudios.primaxcore.menu.scale.MenuScale;
import net.primaxstudios.primaxcore.menu.scale.RowMenuScale;
import net.primaxstudios.primaxcore.menu.scale.SizeMenuScale;
import net.primaxstudios.primaxcore.placeholder.Placeholder;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.meta.ItemMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public final class MenuUtils {

    private static final Logger logger = LoggerFactory.getLogger(MenuUtils.class);

    private MenuUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static MenuScale createMenuScale(Section section) {
        if (section.contains("menu_size")) {
            int size = section.getInt("menu_size");
            return new SizeMenuScale(size);
        }else if (section.contains("menu_rows")) {
            int rows = section.getInt("menu_rows");
            return new RowMenuScale(rows);
        }else if (section.contains("menu_type")) {
            InventoryType type = InventoryType.valueOf(section.getString("menu_type").toUpperCase(Locale.ROOT));
            return new InventoryMenuScale(type);
        }else {
            logger.warn("Missing 'size' or 'rows' key in section '{}' of '{}'", section.getName(), section.getRoot().getFile());
            throw new RuntimeException();
        }
    }

    public static Placeholder getItemPlaceholder(Material material, ItemMeta meta) {
        String displayName = meta.hasDisplayName() ? ColorUtils.color(meta.displayName()) : getMaterialDisplayName(material);
        List<String> lore = meta.hasLore() ? Objects.requireNonNull(meta.lore()).stream().map(ColorUtils::color).toList() : new ArrayList<>();
        List<String> enchants = meta.getEnchants().entrySet().stream()
                .map(entry -> entry.getKey().displayName(entry.getValue()))
                .map(ColorUtils::color)
                .toList();

        Placeholder placeholder = new Placeholder();
        placeholder.addReplacement("{item_display_name}", displayName);
        placeholder.addReplacement("{item_lore}", lore);
        placeholder.addReplacement("{item_enchants}", enchants);
        return placeholder;
    }

    public static String getMaterialDisplayName(Material material) {
        Component displayName = Component.translatable(material.translationKey());
        String rawDisplayName = ColorUtils.color(displayName);

        if (displayName.color() != null) {
            return rawDisplayName;
        }else {
            return "<white>" + rawDisplayName + "</white>";
        }
    }
}
