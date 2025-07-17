package net.primaxstudios.primaxcore.managers;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxcore.functions.MobFunction;
import net.primaxstudios.primaxcore.registries.MobFunctionRegistry;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class MobFunctionManager {

    private static final Logger logger = LoggerFactory.getLogger(MobFunctionManager.class);
    private final MobFunctionRegistry registry = new MobFunctionRegistry();

    public <T extends MobFunction> T getFunction(JavaPlugin plugin, Section section, Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor(JavaPlugin.class, Section.class);
            return constructor.newInstance(plugin, section);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.error("Failed to instantiate MobFunction for class: '{}' from section '{}'", clazz.getName(), section.getName(), e);
            throw new RuntimeException(e);
        }
    }

    public MobFunction getFunction(JavaPlugin plugin, Section section) {
        Key key = ConfigUtils.parseKey(section, "type");
        if (key == null) {
            logger.warn("Failed to get key from section '{}'", section.getName());
            throw new RuntimeException();
        }
        Class<? extends MobFunction> functionClass = registry.getObject(key);
        if (functionClass == null) {
            logger.warn("No function class registered for key: {}", key);
            throw new RuntimeException();
        }
        return getFunction(plugin, section, functionClass);
    }

    public List<MobFunction> getFunctions(JavaPlugin plugin, Section functionsSection) {
        List<MobFunction> functions = new ArrayList<>();
        for (String route : functionsSection.getRoutesAsStrings(false)) {
            Section section = functionsSection.getSection(route);
            if (section == null) {
                logger.warn("Section is null for route: {} in section '{}' of '{}'", route, functionsSection.getName(), functionsSection.getRoot().getFile());
                throw new RuntimeException();
            }
            functions.add(getFunction(plugin, section));
        }
        return functions;
    }
}
