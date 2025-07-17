package net.primaxstudios.primaxcore.managers;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxcore.actions.Action;
import net.primaxstudios.primaxcore.actions.types.ActionType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ActionManager {

    private final ActionTypeManager actionTypeManager = new ActionTypeManager();

    public Action getAction(JavaPlugin plugin, Section section) {
        List<ActionType> actionTypes = actionTypeManager.getActionTypes(plugin, section);
        return new Action(actionTypes);
    }
}