package net.primaxstudios.primaxcore.locale;

import net.primaxstudios.primaxcore.placeholders.Placeholder;
import net.primaxstudios.primaxcore.utils.ColorUtils;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

@Getter
public class Locale {

    private Map<String, String> messageById = new HashMap<>();

    public void reload(JavaPlugin plugin) {
        try {
            this.messageById = loadMessageById(getDocument(plugin));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getSimpleMessage(String id) {
        return messageById.get(id);
    }

    public Component getMessage(String id) {
        String simpleMessage = getSimpleMessage(id);
        if (simpleMessage == null) {
            return null;
        }
        return ColorUtils.getComponent(simpleMessage);
    }

    public void sendMessage(Audience audience, String id) {
        if (audience == null) {
            return;
        }
        Component message = getMessage(id);
        if (message == null) {
            return;
        }
        audience.sendMessage(message);
    }

    public void broadcastMessage(String id) {
        Component message = getMessage(id);
        if (message == null) {
            return;
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }

    public Component getPlaceholderMessage(String id, Placeholder placeholder) {
        String simpleMessage = getSimpleMessage(id);
        if (simpleMessage == null) {
            return null;
        }
        return ColorUtils.getComponent(placeholder.setPlaceholders(simpleMessage));
    }

    public void sendPlaceholderMessage(Audience audience, String id, Placeholder placeholder) {
        if (audience == null) {
            return;
        }
        Component message = getPlaceholderMessage(id, placeholder);
        if (message == null) {
            return;
        }
        audience.sendMessage(message);
    }

    public void broadcastPlaceholderMessage(String id, Placeholder placeholder) {
        Component message = getPlaceholderMessage(id, placeholder);
        if (message == null) {
            return;
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }

    public String getSimplePlaceholderMessage(String id, Placeholder placeholder) {
        String simpleMessage = getSimpleMessage(id);
        if (simpleMessage == null) {
            return null;
        }
        return placeholder.setPlaceholders(simpleMessage);
    }

    private Map<String, String> loadMessageById(Section section) {
        Map<String, String> messageById = new HashMap<>();
        for (String route : section.getRoutesAsStrings(false)) {
            if (section.isList(route)) {
                String message = loadMessages(section.getStringList(route));
                messageById.put(route, message);
            }else {
                String message = section.getString(route);
                messageById.put(route, message);
            }
        }
        return messageById;
    }

    private String loadMessages(List<String> list) {
        StringJoiner joiner = new StringJoiner("\n");
        for (String message : list) {
            joiner.add(message);
        }
        return joiner.toString();
    }

    public void addMessage(String id, String message) {
        messageById.put(id, message);
    }

    public void addMessage(String id, List<String> list) {
        StringJoiner joiner = new StringJoiner("\n");
        for (String message : list) {
            joiner.add(message);
        }
        addMessage(id, joiner.toString());
    }

    public void removeMessage(String id) {
        messageById.remove(id);
    }

    public void clear() {
        messageById.clear();
    }

    private String getName(JavaPlugin plugin) throws IOException {
        return ConfigUtils.load(plugin, "config.yml").getString("lang");
    }

    private YamlDocument getDocument(JavaPlugin plugin) throws IOException {
        return ConfigUtils.load(plugin, "locale/" + getName(plugin) + ".yml");
    }
}