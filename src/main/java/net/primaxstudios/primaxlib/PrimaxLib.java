package net.primaxstudios.primaxlib;

import com.alessiodp.libby.BukkitLibraryManager;
import com.alessiodp.libby.Library;
import net.primaxstudios.primaxlib.listener.MenuListener;
import net.primaxstudios.primaxlib.locale.Locale;
import lombok.Getter;
import net.primaxstudios.primaxlib.manager.DatabaseManager;
import net.primaxstudios.primaxlib.manager.RedisManager;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.List;

@Getter
public abstract class PrimaxLib extends JavaPlugin {

    private static NamespacedKey identifierKey;

    @Override
    public void onEnable() {
        BukkitLibraryManager libraryManager = new BukkitLibraryManager(this);
        libraries().forEach(libraryManager::loadLibrary);

        saveConfigs();

        identifierKey = new NamespacedKey(getNamespace(), "key");

        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            try {
                if (isDatabaseRequired()) {
                    DatabaseManager.init(this);
                    DatabaseManager.get().connect();
                }
                if (isRedisRequired()) {
                    RedisManager.init(this);
                    RedisManager.get().connect();
                }
                onConnect();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Locale.inst().reload(this);

        registerEvents();
    }

    @Override
    public void onDisable() {
        DatabaseManager.shutdown();
    }

    public void reload() {
        Locale.inst().reload(this);
    }

    public abstract String getNamespace();

    public abstract List<Library> libraries();

    public abstract void saveConfigs();

    public abstract boolean isDatabaseRequired();

    public abstract boolean isRedisRequired();

    public abstract void onConnect();

    public void registerEvents() {
        Bukkit.getServer().getPluginManager().registerEvents(new MenuListener(), this);
    }

    public static NamespacedKey getIdentifierKey() {
        return identifierKey;
    }
}
