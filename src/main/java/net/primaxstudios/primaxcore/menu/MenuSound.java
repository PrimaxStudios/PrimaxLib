package net.primaxstudios.primaxcore.menu;

import lombok.Getter;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

@Getter
public class MenuSound {

    private final Sound success;
    private final Sound failure;

    public MenuSound(Sound success, Sound failure) {
        this.success = success;
        this.failure = failure;
    }

    public void playSuccess(Player player) {
        if (success == null) {
            return;
        }
        player.playSound(player.getLocation(), success, SoundCategory.UI, 1F, 1F);
    }

    public void playFailure(Player player) {
        if (failure == null) {
            return;
        }
        player.playSound(player.getLocation(), failure, SoundCategory.UI, 1F, 1F);
    }
}
