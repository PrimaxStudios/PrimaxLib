package net.primaxstudios.primaxlib;

import com.alessiodp.libby.BukkitLibraryManager;
import com.alessiodp.libby.Library;
import net.primaxstudios.primaxlib.database.DatabaseConnector;
import net.primaxstudios.primaxlib.factory.ConnectorFactory;
import net.primaxstudios.primaxlib.listener.MenuListener;
import net.primaxstudios.primaxlib.locale.Locale;
import lombok.Getter;
import net.primaxstudios.primaxlib.manager.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.List;

@Getter
public abstract class PrimaxLib extends JavaPlugin {

    private NamespacedKey identifierKey;
    private DatabaseConnector connector;

    @Override
    public void onEnable() {
        BukkitLibraryManager libraryManager = new BukkitLibraryManager(this);
        libraries().forEach(libraryManager::loadLibrary);

        saveConfigs();

        identifierKey = new NamespacedKey(getNamespace(), "key");

        try {
            if (isDatabaseRequired()) {
                connector = ConnectorFactory.fromPlugin(this);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Locale.inst().reload(this);

        try {
            DatabaseManager.init(this);
            DatabaseManager.get().
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        registerEvents();
    }

    @Override
    public void onDisable() {
        if (connector != null) {
            connector.close();
        }
    }

    public void reload() {
        Locale.inst().reload(this);
    }

    public abstract String getNamespace();

    public abstract List<Library> libraries();

    public abstract void saveConfigs();

    public abstract boolean isDatabaseRequired();

    public void registerEvents() {
        Bukkit.getServer().getPluginManager().registerEvents(new MenuListener(), this);
    }
}
