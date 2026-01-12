package net.primaxstudios.primaxlib.factory;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxlib.database.Credentials;
import net.primaxstudios.primaxlib.database.DatabaseConnector;
import net.primaxstudios.primaxlib.database.PoolSettings;
import net.primaxstudios.primaxlib.database.connector.MySqlConnector;
import net.primaxstudios.primaxlib.database.connector.SqliteConnector;
import net.primaxstudios.primaxlib.util.ConfigUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Objects;

/**
 * Factory for creating {@link DatabaseConnector} instances from configuration.
 */
public final class ConnectorFactory {

    private ConnectorFactory() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static DatabaseConnector fromPlugin(JavaPlugin plugin) throws IOException {
        YamlDocument document = ConfigUtils.load(plugin, "database.yml");
        return fromConfig(plugin, document.getSection("database"));
    }

    public static DatabaseConnector fromConfig(JavaPlugin plugin, Section section) {
        if (!section.getBoolean("enabled", true)) {
            return null;
        }

        String type = Objects.requireNonNull(section.getString("type")).toLowerCase();
        PoolSettings poolSettings = toPoolSettings(section.getSection("pool_settings"));
        Credentials credentials = toCredentials(section.getSection("credentials"));

        return switch (type) {
            case "sqlite"     -> new SqliteConnector(poolSettings, plugin.getDataFolder());
            case "mysql"      -> new MySqlConnector(poolSettings, credentials);
            default -> throw new IllegalArgumentException("Unsupported database type: " + type);
        };
    }

    private static Credentials toCredentials(Section section) {
        return new Credentials(
                section.getString("host"),
                section.getInt("port"),
                section.getString("database"),
                section.getString("username"),
                section.contains("password") ? section.getString("password") : null,
                section.getString("parameters")
        );
    }

    private static PoolSettings toPoolSettings(Section section) {
        return new PoolSettings(
                section.getInt("maximum_pool_size"),
                section.getInt("minimum_idle"),
                section.getInt("maximum_lifetime"),
                section.getInt("keepalive_time"),
                section.getInt("connection_timeout")
        );
    }

    private static Credentials applyMongoSettings(Credentials credentials, Section section) {
        credentials.setParameters(section.getString("parameters"));
        return credentials;
    }
}