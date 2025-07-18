package net.primaxstudios.primaxcore.registries.types;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ClassIdClassRegistry<T> {

    private final Map<Class<?>, IdClassRegistry<T>> registryByClass = new HashMap<>();

    public boolean register(Class<?> aClass, String id, Class<? extends T> object) {
        registryByClass.computeIfAbsent(aClass, k -> new IdClassRegistry<>()).register(id, object);
        return true;
    }

    public Class<? extends T> getClass(Class<?> aClass, String id) {
        return registryByClass.getOrDefault(aClass, new IdClassRegistry<>()).getClass(id);
    }

    public IdClassRegistry<T> getRegistry(Class<?> aClass) {
        return registryByClass.get(aClass);
    }

    public List<IdClassRegistry<T>> getRegistries() {
        return new ArrayList<>(registryByClass.values());
    }
}