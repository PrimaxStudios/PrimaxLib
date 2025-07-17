package net.primaxstudios.primaxcore.placeholders;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Getter @Setter
public abstract class PlaceholderObject<T, V> {

    private V rawObject;
    private T originalObject;

    protected PlaceholderObject(@NotNull T originalObject, Object ignored) {
        this.originalObject = Objects.requireNonNull(originalObject);
    }

    protected PlaceholderObject(@NotNull V rawObject) {
        this.rawObject = rawObject;
    }

    public void setRawObject(@NotNull V rawObject) {
        this.originalObject = null;
        this.rawObject = rawObject;
    }

    public void setOriginalObject(@NotNull T originalObject) {
        this.rawObject = null;
        this.originalObject = originalObject;
    }

    protected abstract T getObjectAbstract(@NotNull Placeholder placeholder);

    public T getObject(@NotNull Placeholder placeholder) {
        if (originalObject != null) {
            return originalObject;
        }
        return getObjectAbstract(placeholder);
    }
}