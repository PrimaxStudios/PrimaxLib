package net.primaxstudios.primaxcore.util;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.Locale;

public class PluginUtils {

    public static String getNamespace(Plugin plugin) {
        return plugin.getName().toLowerCase(Locale.ROOT);
    }

    public static Economy findEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return null;
        }
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return null;
        }
        return rsp.getProvider();
    }

    public static Permission findPermission() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return null;
        }
        RegisteredServiceProvider<Permission> rsp = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp == null) {
            return null;
        }
        return rsp.getProvider();
    }
}
