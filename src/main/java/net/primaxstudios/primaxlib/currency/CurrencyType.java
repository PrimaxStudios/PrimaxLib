package net.primaxstudios.primaxlib.currency;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import net.primaxstudios.primaxlib.currency.type.CoinsEngineCurrency;
import net.primaxstudios.primaxlib.currency.type.CustomCurrency;
import net.primaxstudios.primaxlib.currency.type.PlayerPointsCurrency;
import net.primaxstudios.primaxlib.currency.type.VaultCurrency;

import java.util.function.Function;

@Getter
public enum CurrencyType {

    VAULT("vault", (s) -> new VaultCurrency()),
    PLAYER_POINTS("player_points", (s) -> new PlayerPointsCurrency()),
    COINS_ENGINE("coins_engine", CoinsEngineCurrency::fromSection),
    CUSTOM("custom", CustomCurrency::fromSection);

    private final String id;
    private final Function<Section, Currency> factory;

    CurrencyType(String id, Function<Section, Currency> factory) {
        this.id = id;
        this.factory = factory;
    }

    public Currency create(Section section) {
        return factory.apply(section);
    }

    public static Currency fromSection(Section section) {
        String typeStr = section.getString("type");
        if (typeStr == null) {
            throw new IllegalArgumentException("Currency section is missing a 'type' field.");
        }

        CurrencyType type = fromId(typeStr);
        if (type == null) {
            throw new IllegalArgumentException("Unknown currency type: " + typeStr);
        }

        return type.create(section);
    }

    public static CurrencyType fromId(String id) {
        for (CurrencyType type : values()) {
            if (type.getId().equalsIgnoreCase(id)) {
                return type;
            }
        }
        return null;
    }
}
