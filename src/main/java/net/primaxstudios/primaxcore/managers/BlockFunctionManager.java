package net.primaxstudios.primaxcore.managers;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxcore.functions.BlockFunction;
import net.primaxstudios.primaxcore.registries.BlockFunctionRegistry;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import net.primaxstudios.primaxcore.utils.Key;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class BlockFunctionManager {

    private static final Logger logger = LoggerFactory.getLogger(BlockFunctionManager.class);
    private final BlockFunctionRegistry registry = new BlockFunctionRegistry();

    public <T extends BlockFunction> T getFunction(JavaPlugin plugin, Section section, Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor(JavaPlugin.class, Section.class);
            return constructor.newInstance(plugin, section);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            logger.error("Failed to instantiate BlockFunction for class: '{}' from section '{}'", clazz.getName(), section.getName(), e);
            throw new RuntimeException();
        }
    }

    public BlockFunction getFunction(JavaPlugin plugin, Section section) {
        Key key = ConfigUtils.parseKey(section, "type");
        if (key == null) {
            logger.warn("Failed to get key from section '{}'", section.getName());
            throw new RuntimeException();
        }
        Class<? extends BlockFunction> functionClass = registry.getObject(key);
        if (functionClass == null) {
            logger.warn("No function class registered for key: {}", key);
            throw new RuntimeException();
        }
        return getFunction(plugin, section, functionClass);
    }

    public List<BlockFunction> getFunctions(JavaPlugin plugin, Section functionsSection) {
        List<BlockFunction> functions = new ArrayList<>();
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
