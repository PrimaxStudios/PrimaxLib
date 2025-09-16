package net.primaxstudios.primaxcore.placeholder;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.primaxstudios.primaxcore.util.ColorUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

@Getter @Setter
public class Placeholder extends HashMap<String, String> {

    public Placeholder() {}

    public Placeholder(String placeholder, String replacement) {
        addReplacement(placeholder, replacement);
    }

    public Placeholder(String placeholder, List<String> replacement) {
        addReplacement(placeholder, replacement);
    }

    public Placeholder(Placeholder placeholder) {
        addPlaceholder(placeholder);
    }

    public void addReplacement(String placeholder, String replacement) {
        put(placeholder, replacement);
    }

    public void addReplacement(String placeholder, List<String> replacement) {
        put(placeholder, String.join("\n", replacement));
    }

    public void addReplacements(Map<String, String> replacementByPlaceholder) {
        putAll(replacementByPlaceholder);
    }

    public void addPlaceholder(Placeholder placeholder) {
        putAll(placeholder);
    }

    public String setPlaceholders(String text) {
        for (Map.Entry<String, String> entry : entrySet()) {
            String placeholder = entry.getKey();
            while (text.contains(placeholder)) {
                text = text.replace(placeholder, entry.getValue());
            }
        }
        return text;
    }

    public List<String> setPlaceholders(List<String> texts) {
        return texts.stream().map(this::setPlaceholders).collect(Collectors.toList());
    }

    public void setPlaceholders(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        setPlaceholders(meta);
        item.setItemMeta(meta);
    }

    public void setPlaceholders(ItemMeta meta) {
        if (meta.hasDisplayName()) {
            String displayName = setPlaceholders(ColorUtils.color(meta.displayName()));
            meta.displayName(ColorUtils.getComponent(displayName));
        }

        List<Component> lore = meta.lore();
        if (lore != null) {
            List<Component> newLore = lore.stream()
                    .map(ColorUtils::color)
                    .map(this::setPlaceholders)
                    .flatMap(line -> Arrays.stream(line.split("\n")))
                    .map(ColorUtils::getComponent)
                    .filter(comp -> !ColorUtils.plainText(comp).isEmpty())
                    .toList();
            meta.lore(newLore);
        }
    }
}
