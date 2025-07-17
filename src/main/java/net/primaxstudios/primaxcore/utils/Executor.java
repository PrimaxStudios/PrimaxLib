package net.primaxstudios.primaxcore.utils;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public abstract class Executor {

    public abstract OfflinePlayer getOfflinePlayer();

    public Player getPlayer() {
        OfflinePlayer offlinePlayer = getOfflinePlayer();
        if (offlinePlayer == null) {
            return null;
        }
        return offlinePlayer.getPlayer();
    }
}
