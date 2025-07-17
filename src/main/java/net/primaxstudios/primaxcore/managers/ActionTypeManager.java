package net.primaxstudios.primaxcore.managers;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxcore.actions.types.ActionType;
import net.primaxstudios.primaxcore.registries.ActionTypeRegistry;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ActionTypeManager {

    private final ActionTypeRegistry registry = new ActionTypeRegistry();

    public <T extends ActionType> T getActionType(JavaPlugin plugin, Section section, Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor(JavaPlugin.class, Section.class);
            return constructor.newInstance(plugin, section);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public ActionType getActionType(JavaPlugin plugin, Section section) {
        String type = section.getString("type");
        Class<? extends ActionType> clazz = registry.getClass(type);
        return getActionType(plugin, section, clazz);
    }

    public List<ActionType> getActionTypes(JavaPlugin plugin, Section actionSection) {
        List<ActionType> actionTypes = new ArrayList<>();
        for (String route : actionSection.getRoutesAsStrings(false)) {
            Section section = actionSection.getSection(route);
            actionSection.adaptKey(getActionType(plugin, section));
        }
        return actionTypes;
    }
}