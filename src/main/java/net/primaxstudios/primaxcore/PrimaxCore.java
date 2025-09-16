package net.primaxstudios.primaxcore;

import net.primaxstudios.primaxcore.listeners.MenuListener;
import net.primaxstudios.primaxcore.locale.Locale;
import net.primaxstudios.primaxcore.managers.*;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public abstract class PrimaxCore extends JavaPlugin {

    private static PrimaxCore instance;
    private Locale locale;
    private DatabaseManager databaseManager;
    private RandomizerManager randomizerManager;
    private CurrencyManager currencyManager;
    private ItemManager itemManager;
    private MenuManager menuManager;

    @Override
    public void onEnable() {
        instance = this;

        locale = new Locale();
        locale.reload(this);

        databaseManager = new DatabaseManager();

        randomizerManager = new RandomizerManager();

        currencyManager = new CurrencyManager();

        itemManager = new ItemManager();

        menuManager = new MenuManager();
    }

    public abstract String getNamespace();

    public NamespacedKey getIdentifierKey() {
        return new NamespacedKey(getNamespace(), "key");
    }

    public void registerEvents() {
        Bukkit.getServer().getPluginManager().registerEvents(new MenuListener(menuManager), this);
    }

    public void reload() {
        locale.reload(this);
    }

    public static PrimaxCore inst() {
        return instance;
    }
}
