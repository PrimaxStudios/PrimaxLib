package net.primaxstudios.primaxcore.managers;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxcore.PrimaxCore;
import net.primaxstudios.primaxcore.registries.RequirementTypeRegistry;
import net.primaxstudios.primaxcore.requirements.types.RequirementType;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class RequirementTypeManager {

    private final RequirementTypeRegistry registry = new RequirementTypeRegistry();

    public <T extends RequirementType> T getRequirementType(JavaPlugin plugin, Section section, Class<T> clazz) {
        T requirementType;
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor(JavaPlugin.class, Section.class);
            requirementType = constructor.newInstance(plugin, section);
        }catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        if (section.contains("optional")) {
            requirementType.setOptional(section.getBoolean("optional"));
        }
        if (section.contains("success-actions")) {
            requirementType.setSuccessActions(PrimaxCore.inst().getActionManager().getAction(plugin, section.getSection("success-actions")));
        }
        if (section.contains("deny-actions")) {
            requirementType.setDenyActions(PrimaxCore.inst().getActionManager().getAction(plugin, section.getSection("deny-actions")));
        }
        return requirementType;
    }

    public RequirementType getRequirementType(JavaPlugin plugin, Section section) {
        String type = section.getString("type");
        Class<? extends RequirementType> clazz = registry.getObject(type);
        return getRequirementType(plugin, section, clazz);
    }

    public List<RequirementType> getRequirementTypes(JavaPlugin plugin, Section requirementsSection) {
        List<RequirementType> requirementTypes = new ArrayList<>();
        for (String route : requirementsSection.getRoutesAsStrings(false)) {
            Section section = requirementsSection.getSection(route);
            requirementTypes.add(getRequirementType(plugin, section));
        }
        return requirementTypes;
    }
}
