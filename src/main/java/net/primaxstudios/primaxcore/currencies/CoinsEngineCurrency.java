package net.primaxstudios.primaxcore.currencies;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import org.bukkit.OfflinePlayer;
import su.nightexpress.coinsengine.api.CoinsEngineAPI;

@Getter
public class CoinsEngineCurrency extends Currency {

    private final String currency;

    public CoinsEngineCurrency(Section section) {
        super(section);
        this.currency = section.getString("currency");
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
