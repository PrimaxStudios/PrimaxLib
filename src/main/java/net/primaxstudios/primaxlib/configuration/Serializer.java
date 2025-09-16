package net.primaxstudios.primaxlib.configuration;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

public abstract class Serializer<T> implements TypeSerializer<T> {

    @Override
    public void serialize(@NotNull Type type, @Nullable T obj, @NotNull ConfigurationNode node) {}
}
