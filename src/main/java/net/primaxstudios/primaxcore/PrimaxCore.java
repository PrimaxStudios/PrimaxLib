package net.primaxstudios.primaxcore;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import net.primaxstudios.primaxcore.locale.Locale;
import net.primaxstudios.primaxcore.configs.core.Configs;
import net.primaxstudios.primaxcore.locale.PluginLocale;
import net.primaxstudios.primaxcore.managers.*;
import lombok.Getter;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class PrimaxCore extends JavaPlugin {

    public static final String NAMESPACE = "primaxcore";
    public static final NamespacedKey IDENTIFIER_KEY = new NamespacedKey(NAMESPACE, "key");
    private static PrimaxCore instance;
    private CoreDependencyManager dependencyManager;
    private Economy economy;
    private Permission permission;
    private HeadDatabaseAPI headDatabaseAPI;
    private Configs configs;
    private Locale locale;
    private PluginLocale coreLocale;
    private DatabaseManager databaseManager;
    private RandomizerManager randomizerManager;
    private CurrencyManager currencyManager;
    private ItemManager itemManager;
    private MenuManager menuManager;

    @Override
    public void onEnable() {
        instance = this;

        dependencyManager = new CoreDependencyManager();
        dependencyManager.loadLibraries();

        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            economy = findEconomy();
            permission = findPermission();
        }
        if (getServer().getPluginManager().getPlugin("HeadDatabase") != null) {
            headDatabaseAPI = new HeadDatabaseAPI();
        }

        configs = new Configs();

        locale = new Locale();
        locale.reload(this);

        coreLocale = new PluginLocale(NAMESPACE);

        databaseManager = new DatabaseManager();

        randomizerManager = new RandomizerManager();

        currencyManager = new CurrencyManager();

        itemManager = new ItemManager();

        menuManager = new MenuManager();
    }

    public void reload() {
        configs.reload();
        locale.reload(this);
    }

    private Economy findEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return null;
        }
        return rsp.getProvider();
    }

    private Permission findPermission() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp == null) {
            return null;
        }
        return rsp.getProvider();
    }

    public static PrimaxCore inst() {
        return instance;
    }
}
