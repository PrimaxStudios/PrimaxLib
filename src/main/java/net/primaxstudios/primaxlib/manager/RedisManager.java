package net.primaxstudios.primaxlib.manager;

import net.primaxstudios.primaxlib.caching.RedisConnector;
import net.primaxstudios.primaxlib.factory.RedisFactory;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class RedisManager {

    private static volatile RedisConnector instance;

    private RedisManager() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Initializes the Redis connector.
     * Should be called once during plugin startup.
     */
    public static void init(JavaPlugin plugin) throws IOException {
        if (instance == null) {
            synchronized (RedisManager.class) {
                if (instance == null) {
                    instance = RedisFactory.fromPlugin(plugin);
                    instance.connect();
                }
            }
        }
    }

    /**
     * Returns the Redis connector instance.
     * Throws if init() was never called.
     */
    public static RedisConnector get() {
        if (instance == null) {
            throw new IllegalStateException("CacheManager not initialized. Call init() first.");
        }
        return instance;
    }

    /**
     * Closes the Redis connection (during plugin shutdown).
     */
    public static void shutdown() {
        if (instance != null) {
            instance.close();
            instance = null;
        }
    }
}
