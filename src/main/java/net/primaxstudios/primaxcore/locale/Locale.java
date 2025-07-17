package net.primaxstudios.primaxcore.locale;

import net.primaxstudios.primaxcore.PrimaxCore;
import net.primaxstudios.primaxcore.events.locale.LocaleAddEvent;
import net.primaxstudios.primaxcore.events.locale.LocaleReloadEvent;
import net.primaxstudios.primaxcore.events.locale.LocaleRemoveEvent;
import net.primaxstudios.primaxcore.events.locale.LocaleUseEvent;
import net.primaxstudios.primaxcore.placeholders.Placeholder;
import net.primaxstudios.primaxcore.utils.ColorUtils;
import net.primaxstudios.primaxcore.utils.CommonUtils;
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

    private final Map<String, Map<String, String>> messagesByNamespace = new HashMap<>();

    public void reload(JavaPlugin plugin) {
        String namespace = CommonUtils.getNamespace(plugin);

        clear(namespace);

        Map<String, String> messageById;
        try {
            messageById = loadMessageById(getDocument(plugin));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        LocaleReloadEvent event = new LocaleReloadEvent(namespace, messageById);
        event.callEvent();

        messagesByNamespace.put(namespace, event.getMessageById());
    }

    private String getCoreSimpleMessage(String id) {
        return messagesByNamespace.getOrDefault(PrimaxCore.NAMESPACE, new HashMap<>()).get(id);
    }

    public String getSimpleMessage(String namespace, String id) {
        String message = messagesByNamespace.getOrDefault(namespace, new HashMap<>()).getOrDefault(id, getCoreSimpleMessage(id));
        if (message == null) {
            return null;
        }
        LocaleUseEvent event = new LocaleUseEvent(namespace, id, message);
        event.callEvent();
        return event.getMessage();
    }

    public Component getMessage(String namespace, String id) {
        String simpleMessage = getSimpleMessage(namespace, id);
        if (simpleMessage == null) {
            return null;
        }
        return ColorUtils.getComponent(simpleMessage);
    }

    public void sendMessage(Audience audience, String namespace, String id) {
        if (audience == null) {
            return;
        }
        Component message = getMessage(namespace, id);
        if (message == null) {
            return;
        }
        audience.sendMessage(message);
    }

    public void broadcastMessage(String namespace, String id) {
        Component message = getMessage(namespace, id);
        if (message == null) {
            return;
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }

    public Component getPlaceholderMessage(String namespace, String id, Placeholder placeholder) {
        String simpleMessage = getSimpleMessage(namespace, id);
        if (simpleMessage == null) {
            return null;
        }
        return ColorUtils.getComponent(placeholder.setPlaceholders(simpleMessage));
    }

    public void sendPlaceholderMessage(Audience audience, String namespace, String id, Placeholder placeholder) {
        if (audience == null) {
            return;
        }
        Component message = getPlaceholderMessage(namespace, id, placeholder);
        if (message == null) {
            return;
        }
        audience.sendMessage(message);
    }

    public void broadcastPlaceholderMessage(String namespace, String id, Placeholder placeholder) {
        Component message = getPlaceholderMessage(namespace, id, placeholder);
        if (message == null) {
            return;
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }

    public String getSimplePlaceholderMessage(String namespace, String id, Placeholder placeholder) {
        String simpleMessage = getSimpleMessage(namespace, id);
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

    public void addMessage(String namespace, String id, String message) {
        LocaleAddEvent event = new LocaleAddEvent(namespace, id, message);
        event.callEvent();

        messagesByNamespace.computeIfAbsent(namespace, k -> new HashMap<>()).put(id, event.getMessage());
    }

    public void addMessage(String namespace, String id, List<String> list) {
        StringJoiner joiner = new StringJoiner("\n");
        for (String message : list) {
            joiner.add(message);
        }
        addMessage(namespace, id, joiner.toString());
    }

    public void removeMessage(String namespace, String id) {
        String message = messagesByNamespace.computeIfAbsent(namespace, k -> new HashMap<>()).remove(id);
        if (message == null) {
            return;
        }
        LocaleRemoveEvent event = new LocaleRemoveEvent(namespace, id, message);
        event.callEvent();
    }

    public void clear(String namespace) {
        messagesByNamespace.remove(namespace);
    }

    private String getName(JavaPlugin plugin) throws IOException {
        return ConfigUtils.load(plugin, "config.yml").getString("lang");
    }

    private YamlDocument getDocument(JavaPlugin plugin) throws IOException {
        return ConfigUtils.load(plugin, "locale/" + getName(plugin) + ".yml");
    }
}