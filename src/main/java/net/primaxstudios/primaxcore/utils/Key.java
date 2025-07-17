package net.primaxstudios.primaxcore.utils;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

@Getter
public class Key {

    private final String namespace;
    private final String key;

    public Key(String namespace, String key) {
        if (namespace == null || namespace.isEmpty()) {
            throw new IllegalArgumentException("Namespace cannot be null or empty");
        }
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key cannot be null or empty");
        }

        this.namespace = namespace;
        this.key = key;
    }

    public Key(JavaPlugin plugin, String key) {
        this(CommonUtils.getNamespace(plugin), key);
    }

    public String getNamespace() {
        return namespace;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return namespace + ":" + key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key that)) return false;
        return namespace.equals(that.namespace) && key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, key);
    }

    /**
     * Parses a string into a Key.
     * For example:
     *  - "foo:bar:baz" => namespace="foo", key="bar:baz"
     *  - "foo:bar" => namespace="foo", key="bar"
     *  - "foo" => returns null (must contain at least one ':')
     *
     * @param string the string to parse
     * @return the Key or null if the format is invalid
     */
    public static Key fromString(String string) {
        if (string == null || string.isEmpty()) {
            return null;
        }

        String[] parts = string.split(":", 2);
        if (parts.length < 2) {
            return null; // Must have at least one ':'
        }

        String namespace = parts[0];
        String key = parts[1];

        if (namespace.isEmpty() || key.isEmpty()) {
            return null;
        }

        return new Key(namespace, key);
    }
}
