package net.primaxstudios.primaxcore.placeholders.objects.abstracts;

import net.primaxstudios.primaxcore.placeholders.PlaceholderObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class PlaceholderStringListAbstract<T> extends PlaceholderObject<T, List<String>> {

    protected PlaceholderStringListAbstract(@NotNull T originalObject, Object ignored) {
        super(originalObject, ignored);
    }

    protected PlaceholderStringListAbstract(@NotNull List<String> rawObject) {
        super(rawObject);
    }
}