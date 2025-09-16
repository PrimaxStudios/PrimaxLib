package net.primaxstudios.primaxcore;

import net.primaxstudios.primaxcore.listeners.MenuListener;
import net.primaxstudios.primaxcore.locale.Locale;
import net.primaxstudios.primaxcore.managers.*;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class PrimaxCore {

    public static final String NAMESPACE = "primaxcore";
    public static final NamespacedKey IDENTIFIER_KEY = new NamespacedKey(NAMESPACE, "key");
    private static PrimaxCore instance;
    private final Locale locale;
    private final DatabaseManager databaseManager;
    private final RandomizerManager randomizerManager;
    private final CurrencyManager currencyManager;
    private final ItemManager itemManager;
    private final MenuManager menuManager;

    public PrimaxCore() {
        instance = this;

        locale = new Locale();

        databaseManager = new DatabaseManager();

        randomizerManager = new RandomizerManager();

        currencyManager = new CurrencyManager();

        itemManager = new ItemManager();

        menuManager = new MenuManager();
    }

    public void registerEvents(JavaPlugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(new MenuListener(menuManager), plugin);
    }

    public void reload(JavaPlugin plugin) {
        locale.reload(plugin);
    }

    public static PrimaxCore inst() {
        return instance;
    }
}
