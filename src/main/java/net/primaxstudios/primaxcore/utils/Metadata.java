package net.primaxstudios.primaxcore.utils;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Metadata {

    private final Map<String, Object> objectByKey = new HashMap<>();

    // --- Setters ---

    public void setString(String key, String value) {
        objectByKey.put(key, value);
    }

    public void setInt(String key, int value) {
        objectByKey.put(key, value);
    }

    public void setBoolean(String key, boolean value) {
        objectByKey.put(key, value);
    }

    public void setDouble(String key, double value) {
        objectByKey.put(key, value);
    }

    public void setLong(String key, long value) {
        objectByKey.put(key, value);
    }

    public void setFloat(String key, float value) {
        objectByKey.put(key, value);
    }

    public void setObject(String key, Object value) {
        objectByKey.put(key, value);
    }

    // --- Getters ---

    public String getString(String key) {
        Object value = objectByKey.get(key);
        return value instanceof String ? (String) value : null;
    }

    public Integer getInt(String key) {
        Object value = objectByKey.get(key);
        return value instanceof Integer ? (Integer) value : null;
    }

    public Boolean getBoolean(String key) {
        Object value = objectByKey.get(key);
        return value instanceof Boolean ? (Boolean) value : null;
    }

    public Double getDouble(String key) {
        Object value = objectByKey.get(key);
        return value instanceof Double ? (Double) value : null;
    }

    public Long getLong(String key) {
        Object value = objectByKey.get(key);
        return value instanceof Long ? (Long) value : null;
    }

    public Float getFloat(String key) {
        Object value = objectByKey.get(key);
        return value instanceof Float ? (Float) value : null;
    }

    @SuppressWarnings("unchecked")
    public <T> T getObject(String key) {
        Object value = objectByKey.get(key);
        if (value != null) {
            return (T) value;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T getObject(String key, Class<T> clazz) {
        Object value = objectByKey.get(key);
        if (clazz.isInstance(value)) {
            return (T) value;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T getOrDefault(String key, @NotNull T def) {
        Object value = objectByKey.get(key);
        if (value != null) {
            return (T) value;
        }
        return def;
    }

    // --- Utilities ---

    public boolean hasKey(String key) {
        return objectByKey.containsKey(key);
    }

    public void remove(String key) {
        objectByKey.remove(key);
    }

    public void clear() {
        objectByKey.clear();
    }

    public boolean isEmpty() {
        return objectByKey.isEmpty();
    }

    public int size() {
        return objectByKey.size();
    }

    public void transfer(Metadata data, String key) {
        Object object = getObject(key);
        if (object == null) return;

        data.setObject(key, object);
    }
}
