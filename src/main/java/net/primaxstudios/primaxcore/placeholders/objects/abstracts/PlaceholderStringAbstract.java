package net.primaxstudios.primaxcore.placeholders.objects.abstracts;

import net.primaxstudios.primaxcore.placeholders.PlaceholderObject;
import org.jetbrains.annotations.NotNull;

public abstract class PlaceholderStringAbstract<T> extends PlaceholderObject<T, String> {

    protected PlaceholderStringAbstract(@NotNull T originalObject, Object ignored) {
        super(originalObject, ignored);
    }

    protected PlaceholderStringAbstract(@NotNull String rawObject) {
        super(rawObject);
    }
}
