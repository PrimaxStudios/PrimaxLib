package net.primaxstudios.primaxcore.pdc;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class NamespacedKeyDataType implements PersistentDataType<String, NamespacedKey> {

    @NotNull
    @Override
    public Class<String> getPrimitiveType() {
        return String.class;
    }

    @NotNull
    @Override
    public Class<NamespacedKey> getComplexType() {
        return NamespacedKey.class;
    }

    @NotNull
    @Override
    public String toPrimitive(NamespacedKey complex, @NotNull PersistentDataAdapterContext context) {
        return complex.getNamespace() + ":" + complex.getKey();
    }

    @NotNull
    @Override
    public NamespacedKey fromPrimitive(String primitive, @NotNull PersistentDataAdapterContext context) {
        String[] parts = primitive.split(":", 2);
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid NamespacedKey format: " + primitive);
        }
        return new NamespacedKey(parts[0], parts[1]);
    }
}
