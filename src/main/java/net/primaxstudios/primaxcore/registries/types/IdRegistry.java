package net.primaxstudios.primaxcore.registries.types;

import lombok.Getter;

import java.util.*;

@Getter
public class IdRegistry<T> {

    private final Map<String, T> objectById = new HashMap<>();

    public boolean register(String id, T object) {
        objectById.put(id.toLowerCase(Locale.ROOT), object);
        return true;
    }

    public boolean hasId(String id) {
        return getIds().contains(id);
    }

    public List<String> getIds() {
        return new ArrayList<>(objectById.keySet());
    }

    public List<T> getObjects() {
        return new ArrayList<>(objectById.values());
    }

    public T getObject(String id) {
        return objectById.get(id.toLowerCase(Locale.ROOT));
    }
}
