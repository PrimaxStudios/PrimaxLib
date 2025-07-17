package net.primaxstudios.primaxcore.placeholders.objects;

import net.primaxstudios.primaxcore.placeholders.Placeholder;
import net.primaxstudios.primaxcore.placeholders.objects.abstracts.PlaceholderStringAbstract;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

public class PlaceholderMiniMessage extends PlaceholderStringAbstract<Component> {

    public PlaceholderMiniMessage(@NotNull Component originalObject) {
        super(originalObject, null);
    }

    public PlaceholderMiniMessage(@NotNull String rawObject) {
        super(rawObject);
    }

    @Override
    protected Component getObjectAbstract(@NotNull Placeholder placeholder) {
        return MiniMessage.miniMessage().deserialize(placeholder.setPlaceholders(getRawObject()));
    }
}
