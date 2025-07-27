package net.primaxstudios.primaxcore.utils;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxcore.caches.Redis;
import net.primaxstudios.primaxcore.databases.Credentials;
import net.primaxstudios.primaxcore.databases.DatabaseConnector;
import net.primaxstudios.primaxcore.databases.PoolSettings;
import net.primaxstudios.primaxcore.databases.connectors.MongodbConnector;
import net.primaxstudios.primaxcore.databases.connectors.MySqlConnector;
import net.primaxstudios.primaxcore.databases.connectors.SqliteConnector;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Objects;

public final class DatabaseUtils {

    private DatabaseUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static DatabaseConnector getConnector(JavaPlugin plugin) throws IOException {
        Section config = ConfigUtils.load(plugin, "database.yml");
        return getConnector(plugin, config.getSection("database"));
    }

    public static DatabaseConnector getConnector(JavaPlugin plugin, Section section) {
        if (section.contains("enabled") && !section.getBoolean("enabled")) {
            return null;
        }
        String type = Objects.requireNonNull(section.getString("type")).toLowerCase();
        PoolSettings poolSettings = getPoolSettings(section.getSection("pool-settings"));
        Credentials credentials = getCredentials(section.getSection("credentials"));
        return switch (type) {
            case "sqlite" -> getSqliteConnector(plugin, poolSettings);
            case "mysql" -> getMySqlConnector(poolSettings, credentials);
            case "mongodb" -> getMongoDBConnector(credentials, section.getSection("mongodb-settings"));
            default -> null;
        };
    }

    public static SqliteConnector getSqliteConnector(JavaPlugin plugin, PoolSettings poolSettings) {
        return new SqliteConnector(poolSettings, plugin.getDataFolder());
    }

    public static MySqlConnector getMySqlConnector(PoolSettings poolSettings, Credentials credentials) {
        return new MySqlConnector(poolSettings, credentials);
    }

    public static MongodbConnector getMongoDBConnector(Credentials credentials, Section section) {
        boolean atlas = section.getBoolean("atlas");
        credentials.setParameters(section.getString("parameters"));
        return new MongodbConnector(credentials, atlas);
    }

    private static Credentials getCredentials(Section section) {
        String host = section.getString("host");
        int port = section.getInt("port");
        String database = section.getString("database");
        String username = section.getString("username");
        String password = section.contains("password") ? section.getString("password") : null;
        String parameters = section.getString("parameters");
        return new Credentials(host, port, database, username, password, parameters);
    }

    private static PoolSettings getPoolSettings(Section section) {
        int maximumPoolSize = section.getInt("maximum-pool-size");
        int minimumIdle = section.getInt("minimum-idle");
        int maximumLifetime = section.getInt("maximum-lifetime");
        int keepaliveTime = section.getInt("keepalive-time");
        int connectionTimeout = section.getInt("connection-timeout");
        return new PoolSettings(maximumPoolSize, minimumIdle, maximumLifetime, keepaliveTime, connectionTimeout);
    }

    public static Redis getRedis(JavaPlugin plugin) throws IOException {
        Section config = ConfigUtils.load(plugin, "database.yml");
        return getRedis(config.getSection("redis"));
    }

    public static Redis getRedis(Section section) {
        if (section.contains("enabled") && !section.getBoolean("enabled")) {
            return null;
        }
        String host = section.getString("host");
        int port = section.getInt("port");
        String password = section.getString("password");
        boolean useSSL = section.getBoolean("use-ssl");
        return new Redis(host, port, password, useSSL);
    }
}
