package net.primaxstudios.primaxlib.currency.type;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import net.primaxstudios.primaxlib.currency.Currency;
import org.bukkit.OfflinePlayer;
import su.nightexpress.coinsengine.api.CoinsEngineAPI;

@Getter
public class CoinsEngineCurrency implements Currency {

    private final String currency;

    public CoinsEngineCurrency(String currency) {
        this.currency = currency;
    }

    public static CoinsEngineCurrency fromSection(Section section) {
        String currency = section.getString("currency");
        return new CoinsEngineCurrency(currency);
    }

    @Override
    public boolean deposit(OfflinePlayer offlinePlayer, double amount) {
        return CoinsEngineAPI.addBalance(offlinePlayer.getUniqueId(), currency, amount);
    }

    @Override
    public boolean withdraw(OfflinePlayer offlinePlayer, double amount) {
        return CoinsEngineAPI.removeBalance(offlinePlayer.getUniqueId(), currency, amount);
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        return CoinsEngineAPI.getBalance(offlinePlayer.getUniqueId(), currency);
    }
}
