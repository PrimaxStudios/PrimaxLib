package net.primaxstudios.primaxcore.placeholders.objects;

import net.primaxstudios.primaxcore.placeholders.Placeholder;
import net.primaxstudios.primaxcore.placeholders.objects.abstracts.PlaceholderStringAbstract;
import net.primaxstudios.primaxcore.utils.ColorUtils;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class PlaceholderComponent extends PlaceholderStringAbstract<Component> {

    public PlaceholderComponent(@NotNull Component originalObject) {
        super(originalObject, null);
    }

    public PlaceholderComponent(@NotNull String rawObject) {
        super(rawObject);
    }

    @Override
    protected Component getObjectAbstract(@NotNull Placeholder placeholder) {
        return ColorUtils.getComponent(placeholder.setPlaceholders(getRawObject()));
    }
}
