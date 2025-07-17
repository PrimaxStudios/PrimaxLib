package net.primaxstudios.primaxcore.managers;

import net.primaxstudios.primaxcore.functions.ItemFunction;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import net.primaxstudios.primaxcore.registries.ItemFunctionRegistry;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import net.primaxstudios.primaxcore.utils.Key;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ItemFunctionManager {

    private static final Logger logger = LoggerFactory.getLogger(ItemFunctionManager.class);
    private final ItemFunctionRegistry registry = new ItemFunctionRegistry();

    public <T extends ItemFunction> T getFunction(JavaPlugin plugin, Section section, Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor(JavaPlugin.class, Section.class);
            return constructor.newInstance(plugin, section);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            logger.error("Failed to instantiate ItemFunction for class: {} from section '{}' of '{}'", clazz.getName(), section.getName(), section.getRoot().getFile(), e);
            throw new RuntimeException();
        }
    }

    public ItemFunction getFunction(JavaPlugin plugin, Section section) {
        Key type = ConfigUtils.parseKey(section, "type");
        if (type == null) {
            logger.error("Failed to load ItemFunction: type is null in '{}'.", section.getName());
            throw new RuntimeException();
        }
        Class<? extends ItemFunction> clazz = registry.getObject(type);
        if (clazz == null) {
            logger.error("Failed to load ItemFunction: type '{}' is null in '{}'.", type, section.getName());
            throw new RuntimeException();
        }
        return getFunction(plugin, section, clazz);
    }

    public List<ItemFunction> getFunctions(JavaPlugin plugin, Section functionsSection) {
        List<ItemFunction> functions = new ArrayList<>();
        for (String route : functionsSection.getRoutesAsStrings(false)) {
            Section section = functionsSection.getSection(route);
            if (section == null) {
                logger.error("Failed to load ItemFunction: section '{}' is null in '{}'.", route, functionsSection.getName());
                throw new RuntimeException();
            }
            ItemFunction function = getFunction(plugin, section);
            functions.add(function);
        }
        return functions;
    }
}
