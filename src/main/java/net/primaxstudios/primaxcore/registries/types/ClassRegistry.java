package net.primaxstudios.primaxcore.registries.types;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ClassRegistry<V> extends AbstractMap<Class<? extends V>, V> {

    private final Set<Map.Entry<Class<? extends V>, V>> entries = new HashSet<>();

    @Override
    public @NotNull Set<Entry<Class<? extends V>, V>> entrySet() {
        return entries;
    }
}
