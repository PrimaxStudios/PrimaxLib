package net.primaxstudios.primaxcore.utils;

public interface Keyed {

    Key getKey();

    default boolean containsKey() {
        return getKey() != null;
    }
}
