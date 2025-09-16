package net.primaxstudios.primaxlib.configuration.serializer;

import net.primaxstudios.primaxlib.configuration.Serializer;
import net.primaxstudios.primaxlib.factory.CustomItemFactory;
import net.primaxstudios.primaxlib.item.CustomItem;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.lang.reflect.Type;

public class CustomItemSerializer extends Serializer<CustomItem> {

    @Override
    public CustomItem deserialize(@NotNull Type type, @NotNull ConfigurationNode node) throws SerializationException {
        return CustomItemFactory.fromSection(node);
    }
}
