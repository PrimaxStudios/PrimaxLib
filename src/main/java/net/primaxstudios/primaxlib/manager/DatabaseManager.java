package net.primaxstudios.primaxlib.manager;

import net.primaxstudios.primaxlib.database.DatabaseConnector;
import net.primaxstudios.primaxlib.factory.ConnectorFactory;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class DatabaseManager {

    // volatile ensures visibility across threads
    private static volatile DatabaseConnector instance;

    private DatabaseManager() {
        // Prevent instantiation
    }

    /**
     * Initializes the database connector for the plugin.
     * Should be called once during plugin startup.
     */
    public static void init(JavaPlugin plugin) throws IOException {
        if (instance == null) {
            synchronized (DatabaseManager.class) {
                if (instance == null) {
                    instance = ConnectorFactory.fromPlugin(plugin);
                    if (instance != null) {
                        instance.connect();
                    }
                }
            }
        }
    }

    /**
     * Get the singleton database connector.
     * Throws if init() was never called.
     */
    public static DatabaseConnector get() {
        if (instance == null) {
            throw new IllegalStateException("DatabaseManager not initialized. Call init() first.");
        }
        return instance;
    }

    /**
     * Closes the database connection (optional, during plugin shutdown).
     */
    public static void shutdown() {
        if (instance != null) {
            instance.close();
            instance = null;
        }
    }
}