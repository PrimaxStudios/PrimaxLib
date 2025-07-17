package net.primaxstudios.primaxcore.placeholders;

import net.primaxstudios.primaxcore.utils.PlaceholderUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter @Setter
public class Placeholder {

    private final Map<String, String> replacementByPlaceholder = new HashMap<>();
    private final List<PlaceholderMethod> methods = new ArrayList<>();
    private OfflinePlayer offlinePlayer;

    public Placeholder() {
    }

    public Placeholder(Placeholder placeholder) {
        addPlaceholder(placeholder);
    }

    public Placeholder(String placeholder, String replacement) {
        addReplacement(placeholder, replacement);
    }

    public Placeholder(PlaceholderMethod method) {
        addMethod(method);
    }

    public Placeholder(OfflinePlayer offlinePlayer) {
        this.offlinePlayer = offlinePlayer;
    }

    public void addReplacement(String placeholder, String replacement) {
        replacementByPlaceholder.put(placeholder, replacement);
    }

    public void addReplacements(Map<String, String> replacementByPlaceholder) {
        for (Map.Entry<String, String> entry : replacementByPlaceholder.entrySet()) {
            addReplacement(entry.getKey(), entry.getValue());
        }
    }

    public void addMethod(PlaceholderMethod method) {
        methods.add(method);
    }

    public void addMethods(List<PlaceholderMethod> methods) {
        this.methods.addAll(methods);
    }

    public String setPlaceholders(String line) {
        for (Map.Entry<String, String> entry : replacementByPlaceholder.entrySet()) {
            while (line.contains(entry.getKey())) {
                line = line.replace(entry.getKey(), entry.getValue());
            }
        }
        for (PlaceholderMethod method : methods) {
            line = method.setPlaceholders(line);
        }
        line = PlaceholderUtils.setPlaceholder(line, offlinePlayer);
        return line;
    }

    public List<String> setPlaceholders(List<String> list) {
        List<String> newList = new ArrayList<>();
        for (String line : list) {
            newList.add(setPlaceholders(line));
        }
        return newList;
    }

    public void addPlaceholder(Placeholder placeholder) {
        addReplacements(placeholder.getReplacementByPlaceholder());
        addMethods(placeholder.getMethods());
        if (offlinePlayer == null) {
            offlinePlayer = placeholder.getOfflinePlayer();
        }
    }
}