package net.primaxstudios.primaxcore.placeholders.objects;

import net.primaxstudios.primaxcore.placeholders.Placeholder;
import net.primaxstudios.primaxcore.placeholders.objects.abstracts.PlaceholderStringAbstract;
import org.jetbrains.annotations.NotNull;

public class PlaceholderDouble extends PlaceholderStringAbstract<Double> {

    public PlaceholderDouble(double originalObject) {
        super(originalObject, null);
    }

    public PlaceholderDouble(@NotNull String rawObject) {
        super(rawObject);
    }

    @Override
    protected Double getObjectAbstract(@NotNull Placeholder placeholder) {
        return Double.parseDouble(placeholder.setPlaceholders(getRawObject()));
    }
}
