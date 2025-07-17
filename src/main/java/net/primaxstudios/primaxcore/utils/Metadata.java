package net.primaxstudios.primaxcore.utils;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Metadata {

    private final Map<Key, Object> objectByKey = new HashMap<>();

    // --- Setters ---

    public void setString(Key key, String value) {
        objectByKey.put(key, value);
    }

    public void setInt(Key key, int value) {
        objectByKey.put(key, value);
    }

    public void setBoolean(Key key, boolean value) {
        objectByKey.put(key, value);
    }

    public void setDouble(Key key, double value) {
        objectByKey.put(key, value);
    }

    public void setLong(Key key, long value) {
        objectByKey.put(key, value);
    }

    public void setFloat(Key key, float value) {
        objectByKey.put(key, value);
    }

    public void setObject(Key key, Object value) {
        objectByKey.put(key, value);
    }

    // --- Getters ---

    public String getString(Key key) {
        Object value = objectByKey.get(key);
        return value instanceof String ? (String) value : null;
    }

    public Integer getInt(Key key) {
        Object value = objectByKey.get(key);
        return value instanceof Integer ? (Integer) value : null;
    }

    public Boolean getBoolean(Key key) {
        Object value = objectByKey.get(key);
        return value instanceof Boolean ? (Boolean) value : null;
    }

    public Double getDouble(Key key) {
        Object value = objectByKey.get(key);
        return value instanceof Double ? (Double) value : null;
    }

    public Long getLong(Key key) {
        Object value = objectByKey.get(key);
        return value instanceof Long ? (Long) value : null;
    }

    public Float getFloat(Key key) {
        Object value = objectByKey.get(key);
        return value instanceof Float ? (Float) value : null;
    }

    @SuppressWarnings("unchecked")
    public <T> T getObject(Key key, Class<T> clazz) {
        Object value = objectByKey.get(key);
        if (clazz.isInstance(value)) {
            return (T) value;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T getOrDefault(Key key, T def) {
        Object value = objectByKey.get(key);
        if (def.getClass().isInstance(value)) {
            return (T) value;
        }
        return def;
    }

    // --- Utilities ---

    public boolean hasKey(Key key) {
        return objectByKey.containsKey(key);
    }

    public void remove(Key key) {
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
}
