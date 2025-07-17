package net.primaxstudios.primaxcore.pdc;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class KeyDataType implements PersistentDataType<String, Key> {

    @Override
    public @NotNull Class<String> getPrimitiveType() {
        return String.class;
    }

    @Override
    public @NotNull Class<Key> getComplexType() {
        return Key.class;
    }

    @Override
    public @NotNull String toPrimitive(Key complex, @NotNull PersistentDataAdapterContext context) {
        return complex.toString(); // e.g., "minecraft:stone"
    }

    @Override
    public @NotNull Key fromPrimitive(String primitive, @NotNull PersistentDataAdapterContext context) {
        String[] parts = primitive.split(":");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid NamespacedKey string: " + primitive);
        }
        return new Key(parts[0], parts[1]);
    }
}
