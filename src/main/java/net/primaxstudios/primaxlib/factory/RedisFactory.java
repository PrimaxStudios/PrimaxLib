package net.primaxstudios.primaxlib.factory;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxlib.caching.RedisConnector;
import net.primaxstudios.primaxlib.util.ConfigUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

/**
 * Factory for creating {@link RedisConnector} instances from configuration.
 */
public final class RedisFactory {

    private RedisFactory() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static RedisConnector fromPlugin(JavaPlugin plugin) throws IOException {
        YamlDocument document = ConfigUtils.load(plugin, "database.yml");
        return fromConfig(document.getSection("redis"));
    }

    public static RedisConnector fromConfig(Section section) {
        if (!section.getBoolean("enabled", true)) {
            return null;
        }

        return new RedisConnector(
                section.getString("host"),
                section.getInt("port"),
                section.getString("password"),
                section.getBoolean("use_ssl")
        );
    }
}