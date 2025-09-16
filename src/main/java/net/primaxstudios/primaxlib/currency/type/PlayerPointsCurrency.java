package net.primaxstudios.primaxlib.currency.type;

import net.primaxstudios.primaxlib.currency.Currency;
import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.OfflinePlayer;

public class PlayerPointsCurrency implements Currency {

    @Override
    public boolean deposit(OfflinePlayer offlinePlayer, double amount) {
        return PlayerPoints.getInstance().getAPI().give(offlinePlayer.getUniqueId(), (int) amount);
    }

    @Override
    public boolean withdraw(OfflinePlayer offlinePlayer, double amount) {
        return PlayerPoints.getInstance().getAPI().take(offlinePlayer.getUniqueId(), (int) amount);
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        return PlayerPoints.getInstance().getAPI().look(offlinePlayer.getUniqueId());
    }
}
