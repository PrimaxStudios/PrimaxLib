package net.primaxstudios.primaxlib.currency.type;

import net.milkbowl.vault.economy.Economy;
import net.primaxstudios.primaxlib.currency.Currency;
import net.primaxstudios.primaxlib.util.PluginUtils;
import org.bukkit.OfflinePlayer;

import java.util.Objects;

public class VaultCurrency implements Currency {

    private final Economy economy;

    public VaultCurrency() {
        this.economy = Objects.requireNonNull(PluginUtils.findEconomy(), "No Vault-compatible economy provider found.");
    }

    @Override
    public boolean deposit(OfflinePlayer offlinePlayer, double amount) {
        economy.depositPlayer(offlinePlayer, amount);
        return true;
    }

    @Override
    public boolean withdraw(OfflinePlayer offlinePlayer, double amount) {
        economy.withdrawPlayer(offlinePlayer, amount);
        return true;
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        return economy.getBalance(offlinePlayer);
    }
}
