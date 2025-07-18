package net.primaxstudios.primaxcore.registries;

import net.primaxstudios.primaxcore.currencies.*;
import net.primaxstudios.primaxcore.registries.types.IdClassRegistry;

public class CurrencyRegistry extends IdClassRegistry<Currency> {

    public CurrencyRegistry() {
        register("vault", VaultCurrency.class);
        register("player_points", PlayerPointsCurrency.class);
        register("coins_engine", CoinsEngineCurrency.class);
        register("custom_currency", CustomCurrency.class);
    }
}