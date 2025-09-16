package net.primaxstudios.primaxcore.registries;

import net.primaxstudios.primaxcore.currencies.*;

import java.util.HashMap;

public class CurrencyRegistry extends HashMap<String, Class<? extends Currency>> {

    public CurrencyRegistry() {
        put("vault", VaultCurrency.class);
        put("player_points", PlayerPointsCurrency.class);
        put("coins_engine", CoinsEngineCurrency.class);
        put("custom_currency", CustomCurrency.class);
    }
}