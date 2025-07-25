package net.primaxstudios.primaxcore.managers;

import net.primaxstudios.primaxcore.items.properties.ItemProperty;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import net.primaxstudios.primaxcore.registries.ItemPropertyRegistry;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ItemPropertyManager {

    private final ItemPropertyRegistry registry = new ItemPropertyRegistry();

    private ItemProperty getProperty(String key) {
        return registry.get(key);
    }

    public List<ItemProperty> getProperties(Section section) {
        List<ItemProperty> properties = new ArrayList<>();
        for (String route : section.getRoutesAsStrings(false)) {
            ItemProperty property = getProperty(route);
            if (property == null) {
                continue;
            }
            properties.add(property);
        }
        return properties;
    }
}