package net.primaxstudios.primaxcore.placeholders.objects;

import net.primaxstudios.primaxcore.placeholders.Placeholder;
import net.primaxstudios.primaxcore.placeholders.objects.abstracts.PlaceholderStringAbstract;
import org.jetbrains.annotations.NotNull;

public class PlaceholderString extends PlaceholderStringAbstract<String> {

    public PlaceholderString(@NotNull String originalObject, Object ignored) {
        super(originalObject, ignored);
    }

    public PlaceholderString(@NotNull String rawObject) {
        super(rawObject);
    }

    @Override
    protected String getObjectAbstract(@NotNull Placeholder placeholder) {
        return placeholder.setPlaceholders(getRawObject());
    }
}