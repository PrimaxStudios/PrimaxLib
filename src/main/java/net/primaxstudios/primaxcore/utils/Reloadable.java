package net.primaxstudios.primaxcore.utils;

import org.bukkit.plugin.java.JavaPlugin;

public interface Reloadable<T extends JavaPlugin> {

    void reload(T plugin);
}
