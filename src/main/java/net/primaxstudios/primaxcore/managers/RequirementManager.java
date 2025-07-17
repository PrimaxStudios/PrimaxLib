package net.primaxstudios.primaxcore.managers;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxcore.PrimaxCore;
import net.primaxstudios.primaxcore.requirements.Requirement;
import net.primaxstudios.primaxcore.requirements.types.RequirementType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;

public class RequirementManager {

    private final RequirementTypeManager requirementTypeManager = new RequirementTypeManager();

    public Requirement getRequirement(JavaPlugin plugin, Section section) {
        Section requirementsSection = Objects.requireNonNull(section.getSection("requirements"));
        List<RequirementType> requirementTypes = requirementTypeManager.getRequirementTypes(plugin, requirementsSection);
        Requirement requirement = new Requirement(requirementTypes);
        if (section.contains("minimum-requirements")) {
            requirement.setMinimum(section.getInt("minimum-requirements"));
        }
        if (section.contains("stop-at-success")) {
            requirement.setStopAtSuccess(section.getBoolean("stop-at-success"));
        }
        if (section.contains("success-actions")) {
            requirement.setSuccessActions(PrimaxCore.inst().getActionManager().getAction(plugin, section.getSection("success-actions")));
        }
        if (section.contains("deny-actions")) {
            requirement.setDenyActions(PrimaxCore.inst().getActionManager().getAction(plugin, section.getSection("deny-actions")));
        }
        return requirement;
    }
}