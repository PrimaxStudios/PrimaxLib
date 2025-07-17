package net.primaxstudios.primaxcore.utils;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import net.primaxstudios.primaxcore.PrimaxCore;
import net.primaxstudios.primaxcore.actions.Action;
import net.primaxstudios.primaxcore.requirements.Requirement;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Operation {

    private final Requirement requirement;
    private final Action action;

    public Operation(Requirement requirement, Action action) {
        this.requirement = requirement;
        this.action = action;
    }

    public Operation(Requirement requirement) {
        this.requirement = requirement;
        this.action = null;
    }

    public Operation(Action action) {
        this.requirement = null;
        this.action = action;
    }

    public boolean hasRequirements(Executor executor) {
        if (requirement == null) {
            return true;
        }
        return requirement.hasRequirements(executor);
    }

    public void executeActions(Executor executor) {
        if (action == null) {
            return;
        }
        action.execute(executor);
    }

    public boolean execute(Executor executor) {
        if (hasRequirements(executor)) {
            executeActions(executor);
            return true;
        }
        return false;
    }

    public static Operation getOperation(JavaPlugin plugin, Section section, String id) {
        String requirementPath = id + "-requirements";
        String actionPath = id + "-actions";
        boolean containsRequirement = section.contains(requirementPath);
        boolean containsAction = section.contains(actionPath);
        if (!containsRequirement && !containsAction) {
            return null;
        }
        Requirement requirement = null;
        if (containsRequirement) {
            requirement = PrimaxCore.inst().getRequirementManager().getRequirement(plugin, section.getSection(requirementPath));
        }
        Action action = null;
        if (containsAction) {
            action = PrimaxCore.inst().getActionManager().getAction(plugin, section.getSection(actionPath));
        }
        return new Operation(requirement, action);
    }
}
