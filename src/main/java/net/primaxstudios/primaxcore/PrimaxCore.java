package net.primaxstudios.primaxcore;

import com.alessiodp.libby.BukkitLibraryManager;
import com.alessiodp.libby.Library;
import net.primaxstudios.primaxcore.database.DatabaseConnector;
import net.primaxstudios.primaxcore.factory.ConnectorFactory;
import net.primaxstudios.primaxcore.listener.MenuListener;
import net.primaxstudios.primaxcore.locale.Locale;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.List;

@Getter
public abstract class PrimaxCore extends JavaPlugin {

    private static PrimaxCore instance;
    private DatabaseConnector connector;
    private Locale locale;

    @Override
    public void onEnable() {
        instance = this;

        BukkitLibraryManager libraryManager = new BukkitLibraryManager(this);
        libraries().forEach(libraryManager::loadLibrary);

        try {
            if (isDatabaseRequired()) {
                connector = ConnectorFactory.fromPlugin(this);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        locale = new Locale();
        locale.reload(this);
    }

    @Override
    public void onDisable() {
        if (connector != null) {
            connector.close();
        }
    }

    public abstract String getNamespace();

    public abstract List<Library> libraries();

    protected abstract boolean isDatabaseRequired();

    public NamespacedKey getIdentifierKey() {
        return new NamespacedKey(getNamespace(), "key");
    }

    public void registerEvents() {
        Bukkit.getServer().getPluginManager().registerEvents(new MenuListener(), this);
    }

    public void reload() {
        locale.reload(this);
    }

    public static PrimaxCore inst() {
        return instance;
    }
}
