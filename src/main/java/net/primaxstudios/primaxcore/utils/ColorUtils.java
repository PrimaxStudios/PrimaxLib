package net.primaxstudios.primaxcore.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public final class ColorUtils {

    private ColorUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static String color(String message) {
        if (message == null) return null;
        return LegacyComponentSerializer.legacyAmpersand().serialize(getComponent(message));
    }

    public static String color(Component message) {
        if (message == null) return null;
        return LegacyComponentSerializer.legacyAmpersand().serialize(message);
    }

    public static Component getComponent(String message) {
        if (message == null) return null;
        return LegacyComponentSerializer.legacyAmpersand().deserialize(message).applyFallbackStyle(TextDecoration.ITALIC.withState(false));
    }

    public static String plainText(Component component) {
        if (component == null) return null;
        return PlainTextComponentSerializer.plainText().serialize(component);
    }
}
