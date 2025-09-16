package net.primaxstudios.primaxlib.currency;

import org.bukkit.OfflinePlayer;

public interface Currency {

    boolean deposit(OfflinePlayer offlinePlayer, double amount);

    boolean withdraw(OfflinePlayer offlinePlayer, double amount);

    double getBalance(OfflinePlayer offlinePlayer);

    default boolean hasEnough(OfflinePlayer offlinePlayer, double amount) {
        return getBalance(offlinePlayer) >= amount;
    }
}
