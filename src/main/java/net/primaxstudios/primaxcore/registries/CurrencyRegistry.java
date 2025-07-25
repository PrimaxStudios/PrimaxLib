package net.primaxstudios.primaxcore.registries;

import net.primaxstudios.primaxcore.currencies.*;
import net.primaxstudios.primaxcore.registries.types.IdClassRegistry;

public class CurrencyRegistry extends IdClassRegistry<Currency> {

    public CurrencyRegistry() {
        put("vault", VaultCurrency.class);
        put("player_points", PlayerPointsCurrency.class);
        put("coins_engine", CoinsEngineCurrency.class);
        put("custom_currency", CustomCurrency.class);
    }
}