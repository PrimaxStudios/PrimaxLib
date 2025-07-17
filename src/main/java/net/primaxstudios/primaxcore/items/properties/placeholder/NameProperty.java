package net.primaxstudios.primaxcore.items.properties.placeholder;

import net.primaxstudios.primaxcore.items.properties.PlaceholderProperty;
import net.primaxstudios.primaxcore.placeholders.Placeholder;
import net.primaxstudios.primaxcore.placeholders.objects.PlaceholderComponent;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

@Getter
public class NameProperty implements PlaceholderProperty {

    public static final String ID = "name";
    private static final Logger logger = LoggerFactory.getLogger(NameProperty.class);
    private final PlaceholderComponent placeholderName;
    private final Object sectionName;
    private final File file;

    public NameProperty(Section section) {
        this.sectionName = section.getName();
        this.file = section.getRoot().getFile();

        String name = section.getString(ID);
        if (name == null) {
            logger.warn("Missing '{}' key in section '{}' of '{}'", ID, sectionName, file);
            throw new IllegalArgumentException("Missing 'name' in section.");
        }

        this.placeholderName = new PlaceholderComponent(name);
    }

    @Override
    public void setProperty(@NotNull ItemStack item, @NotNull Placeholder placeholder) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            logger.warn("ItemMeta is null for item: {} in section '{}' of '{}'", item.getType(), sectionName, file);
            throw new RuntimeException("ItemMeta is null");
        }

        meta.displayName(placeholderName.getObject(placeholder));
        item.setItemMeta(meta);
    }
}