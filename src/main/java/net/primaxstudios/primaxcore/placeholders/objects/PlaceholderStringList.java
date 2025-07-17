package net.primaxstudios.primaxcore.placeholders.objects;

import net.primaxstudios.primaxcore.placeholders.Placeholder;
import net.primaxstudios.primaxcore.placeholders.objects.abstracts.PlaceholderStringListAbstract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlaceholderStringList extends PlaceholderStringListAbstract<List<String>> {

    public PlaceholderStringList(@NotNull List<String> originalObject, Object ignored) {
        super(originalObject, ignored);
    }

    public PlaceholderStringList(@NotNull List<String> rawObject) {
        super(rawObject);
    }

    @Override
    protected List<String> getObjectAbstract(@NotNull Placeholder placeholder) {
        return placeholder.setPlaceholders(getRawObject());
    }
}
