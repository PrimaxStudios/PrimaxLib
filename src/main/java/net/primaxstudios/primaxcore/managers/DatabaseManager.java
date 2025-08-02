package net.primaxstudios.primaxcore.managers;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxcore.caches.Redis;
import net.primaxstudios.primaxcore.databases.Credentials;
import net.primaxstudios.primaxcore.databases.DatabaseConnector;
import net.primaxstudios.primaxcore.databases.PoolSettings;
import net.primaxstudios.primaxcore.databases.connectors.MongodbConnector;
import net.primaxstudios.primaxcore.databases.connectors.MySqlConnector;
import net.primaxstudios.primaxcore.databases.connectors.SqliteConnector;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Objects;

public class DatabaseManager {

    public DatabaseConnector getConnector(JavaPlugin plugin) throws IOException {
        YamlDocument document = ConfigUtils.load(plugin, "database.yml");
        return getConnector(plugin, document.getSection("database"));
    }

    public DatabaseConnector getConnector(JavaPlugin plugin, Section section) {
        if (section.contains("enabled") && !section.getBoolean("enabled")) return null;

        String type = Objects.requireNonNull(section.getString("type")).toLowerCase();
        PoolSettings poolSettings = getPoolSettings(section.getSection("pool_settings"));
        Credentials credentials = getCredentials(section.getSection("credentials"));
        return switch (type) {
            case "sqlite" -> getSqliteConnector(plugin, poolSettings);
            case "mysql" -> getMySqlConnector(poolSettings, credentials);
            case "mongodb" -> getMongoDBConnector(credentials, section.getSection("mongodb_settings"));
            default -> null;
        };
    }

    public SqliteConnector getSqliteConnector(JavaPlugin plugin, PoolSettings poolSettings) {
        return new SqliteConnector(poolSettings, plugin.getDataFolder());
    }

    public MySqlConnector getMySqlConnector(PoolSettings poolSettings, Credentials credentials) {
        return new MySqlConnector(poolSettings, credentials);
    }

    public MongodbConnector getMongoDBConnector(Credentials credentials, Section section) {
        boolean atlas = section.getBoolean("atlas");
        credentials.setParameters(section.getString("parameters"));
        return new MongodbConnector(credentials, atlas);
    }

    private Credentials getCredentials(Section section) {
        String host = section.getString("host");
        int port = section.getInt("port");
        String database = section.getString("database");
        String username = section.getString("username");
        String password = section.contains("password") ? section.getString("password") : null;
        String parameters = section.getString("parameters");
        return new Credentials(host, port, database, username, password, parameters);
    }

    private PoolSettings getPoolSettings(Section section) {
        int maximumPoolSize = section.getInt("maximum_pool_size");
        int minimumIdle = section.getInt("minimum_idle");
        int maximumLifetime = section.getInt("maximum_lifetime");
        int keepaliveTime = section.getInt("keepalive_time");
        int connectionTimeout = section.getInt("connection_timeout");
        return new PoolSettings(maximumPoolSize, minimumIdle, maximumLifetime, keepaliveTime, connectionTimeout);
    }

    public Redis getRedis(JavaPlugin plugin) throws IOException {
        Section config = ConfigUtils.load(plugin, "database.yml");
        return getRedis(config.getSection("redis"));
    }

    public Redis getRedis(Section section) {
        if (section.contains("enabled") && !section.getBoolean("enabled")) return null;

        String host = section.getString("host");
        int port = section.getInt("port");
        String password = section.getString("password");
        boolean useSSL = section.getBoolean("use_ssl");
        return new Redis(host, port, password, useSSL);
    }
}
