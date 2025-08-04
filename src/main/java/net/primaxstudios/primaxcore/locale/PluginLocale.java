package net.primaxstudios.primaxcore.locale;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.primaxstudios.primaxcore.PrimaxCore;
import net.primaxstudios.primaxcore.placeholders.Placeholder;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class PluginLocale {

    private final String namespace;

    public PluginLocale(String namespace) {
        this.namespace = namespace;
    }

    public void reload(JavaPlugin plugin) {
        PrimaxCore.inst().getLocale().reload(plugin);
    }

    public String getSimpleMessage(String id) {
        return PrimaxCore.inst().getLocale().getSimpleMessage(namespace, id);
    }

    public Component getMessage(String namespace, String id) {
        return PrimaxCore.inst().getLocale().getMessage(namespace, id);
    }

    public void sendMessage(Audience audience, String id) {
        PrimaxCore.inst().getLocale().sendMessage(audience, namespace, id);
    }

    public void broadcastMessage(String id) {
        PrimaxCore.inst().getLocale().broadcastMessage(namespace, id);
    }

    public Component getPlaceholderMessage(String id, Placeholder placeholder) {
        return PrimaxCore.inst().getLocale().getPlaceholderMessage(namespace, id, placeholder);
    }

    public void sendPlaceholderMessage(Audience audience, String id, Placeholder placeholder) {
        PrimaxCore.inst().getLocale().sendPlaceholderMessage(audience, namespace, id, placeholder);
    }

    public void broadcastPlaceholderMessage(String id, Placeholder placeholder) {
        PrimaxCore.inst().getLocale().broadcastPlaceholderMessage(namespace, id, placeholder);
    }

    public String getSimplePlaceholderMessage(String id, Placeholder placeholder) {
        return PrimaxCore.inst().getLocale().getSimplePlaceholderMessage(namespace, id, placeholder);
    }

    public void addMessage(String id, String message) {
        PrimaxCore.inst().getLocale().addMessage(namespace, id, message);
    }

    public void addMessage(String id, List<String> list) {
        PrimaxCore.inst().getLocale().addMessage(namespace, id, list);
    }

    public void removeMessage(String id) {
        PrimaxCore.inst().getLocale().removeMessage(namespace, id);
    }

    public void clear() {
        PrimaxCore.inst().getLocale().clear(namespace);
    }
}
