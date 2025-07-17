package net.primaxstudios.primaxcore.managers;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import net.primaxstudios.primaxcore.functions.MenuFunction;
import net.primaxstudios.primaxcore.registries.MenuFunctionRegistry;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class MenuFunctionManager {

    private static final Logger logger = LoggerFactory.getLogger(MenuFunctionManager.class);
    private final MenuFunctionRegistry registry = new MenuFunctionRegistry();

    public <T extends MenuFunction> T getFunction(JavaPlugin plugin, Section section, Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor(JavaPlugin.class, Section.class);
            return constructor.newInstance(plugin, section);
        }catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            logger.error("Failed to instantiate MenuFunction for class: '{}' from section '{}'", clazz.getName(), section.getName(), e);
            throw new RuntimeException();
        }
    }

    public MenuFunction getFunction(JavaPlugin plugin, Section section) {
        Key type = ConfigUtils.parseKey(section, "type");
        if (type == null) {
            logger.error("Failed to load MenuFunction: type is null in '{}'.", section.getName());
            throw new RuntimeException();
        }
        Class<? extends MenuFunction> clazz = registry.getObject(type);
        if (clazz == null) {
            logger.error("Failed to load MenuFunction: class is null in '{}'.", section.getName());
            throw new RuntimeException();
        }
        return getFunction(plugin, section, clazz);
    }

    public List<MenuFunction> getFunctions(JavaPlugin plugin, Section functionsSection) {
        List<MenuFunction> functions = new ArrayList<>();
        for (String route : functionsSection.getRoutesAsStrings(false)) {
            Section section = functionsSection.getSection(route);
            if (section == null) {
                logger.error("Failed to load MenuFunction: section '{}' is null in '{}'.", route, functionsSection.getName());
                throw new RuntimeException();
            }
            MenuFunction function = getFunction(plugin, section);
            functions.add(function);
        }
        return functions;
    }
}
