package net.primaxstudios.primaxcore.currencies;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.milkbowl.vault.economy.Economy;
import net.primaxstudios.primaxcore.utils.PluginUtils;
import org.bukkit.OfflinePlayer;

public class VaultCurrency extends Currency {

    private final Economy economy;

    public VaultCurrency(Section section) {
        super(section);
        this.economy = PluginUtils.findEconomy();
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
