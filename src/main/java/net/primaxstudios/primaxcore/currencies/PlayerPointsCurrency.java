package net.primaxstudios.primaxcore.currencies;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.OfflinePlayer;

public class PlayerPointsCurrency extends Currency {

    public PlayerPointsCurrency(Section section) {
        super(section);
    }

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
