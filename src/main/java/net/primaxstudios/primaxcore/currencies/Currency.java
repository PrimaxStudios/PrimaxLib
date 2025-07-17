package net.primaxstudios.primaxcore.currencies;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.OfflinePlayer;

public abstract class Currency {

    protected Currency() {
    }

    protected Currency(Section section) {
    }

    public abstract boolean deposit(OfflinePlayer offlinePlayer, double amount);

    public abstract boolean withdraw(OfflinePlayer offlinePlayer, double amount);

    public abstract double getBalance(OfflinePlayer offlinePlayer);

    public boolean hasEnough(OfflinePlayer offlinePlayer, double amount) {
        return getBalance(offlinePlayer) >= amount;
    }
}
