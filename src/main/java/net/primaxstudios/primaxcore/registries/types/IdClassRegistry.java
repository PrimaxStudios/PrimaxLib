package net.primaxstudios.primaxcore.registries.types;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class IdClassRegistry<T> extends IdRegistry<Class<? extends T>> {

    public List<Class<? extends T>> getClasses() {
        return super.getObjects();
    }

    public Class<? extends T> getClass(String id) {
        return super.getObject(id);
    }

    public Map<String, Class<? extends T>> getClassById() {
        return super.getObjectById();
    }
}