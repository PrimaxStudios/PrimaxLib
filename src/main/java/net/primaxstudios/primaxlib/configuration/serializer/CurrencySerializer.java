package net.primaxstudios.primaxlib.configuration.serializer;

import net.primaxstudios.primaxlib.configuration.Serializer;
import net.primaxstudios.primaxlib.currency.Currency;
import net.primaxstudios.primaxlib.currency.CurrencyType;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.lang.reflect.Type;

public class CurrencySerializer extends Serializer<Currency> {

    @Override
    public Currency deserialize(@NotNull Type type, @NotNull ConfigurationNode node) throws SerializationException {
        String typeStr = node.node("type").getString();
        if (typeStr == null) {
            throw new SerializationException("Currency section is missing a 'type' field.");
        }

        CurrencyType currencyType = CurrencyType.fromId(typeStr);
        if (currencyType == null) {
            throw new SerializationException("Unknown currency type: " + typeStr);
        }

        return currencyType.create(node);
    }
}
