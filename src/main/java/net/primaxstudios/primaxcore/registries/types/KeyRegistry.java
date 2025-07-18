package net.primaxstudios.primaxcore.registries.types;

import lombok.Getter;
import net.primaxstudios.primaxcore.utils.Key;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class KeyRegistry<T> {

    private final Map<String, Map<Key, T>> objects = new HashMap<>();

    public boolean register(Key key, T object) {
        objects.computeIfAbsent(key.getNamespace(), k -> new HashMap<>()).put(key, object);
        return true;
    }

    public boolean unregister(Key key) {
        Map<Key, T> objectById = objects.get(key.getNamespace());
        if (objectById == null) {
            return false;
        }
        return objectById.remove(key) != null;
    }

    public boolean unregisterAll(String namespace) {
        return objects.remove(namespace) != null;
    }

    public void unregisterAll() {
        objects.clear();
    }

    public T getObject(Key key) {
        Map<Key, T> objectById = objects.get(key.getNamespace());
        if (objectById == null) {
            return null;
        }
        return objectById.get(key);
    }

    public List<T> getObjects(String namespace) {
        Map<Key, T> objectById = objects.get(namespace);
        if (objectById == null) {
            return null;
        }
        return new ArrayList<>(objectById.values());
    }

    public List<Key> getKeys(String namespace) {
        Map<Key, T> objectById = objects.get(namespace);
        if (objectById == null) {
            return null;
        }
        return new ArrayList<>(objectById.keySet());
    }

    public List<Key> getKeys() {
        return objects.values().stream()
                .flatMap(objectByKey -> objectByKey.keySet().stream())
                .toList();
    }

    public List<T> getObjects() {
        return objects.values().stream()
                .flatMap(objectByKey -> objectByKey.values().stream())
                .toList();
    }
}