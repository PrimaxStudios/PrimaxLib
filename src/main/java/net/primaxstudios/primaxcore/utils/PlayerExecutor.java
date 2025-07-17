package net.primaxstudios.primaxcore.utils;

import lombok.Getter;
import org.bukkit.OfflinePlayer;

@Getter
public class PlayerExecutor extends Executor {

    private final OfflinePlayer offlinePlayer;

    public PlayerExecutor(OfflinePlayer offlinePlayer) {
        this.offlinePlayer = offlinePlayer;
    }

    @Override
    public OfflinePlayer getOfflinePlayer() {
        return offlinePlayer;
    }
}
