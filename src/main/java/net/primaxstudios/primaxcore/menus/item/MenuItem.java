package net.primaxstudios.primaxcore.menus.item;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxcore.configs.Config;
import net.primaxstudios.primaxcore.events.menu.CustomMenuClickEvent;
import net.primaxstudios.primaxcore.menus.ClickResult;
import net.primaxstudios.primaxcore.menus.MenuHolder;
import net.primaxstudios.primaxcore.menus.MenuSound;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import net.primaxstudios.primaxcore.versions.VersionManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public abstract class MenuItem {

    public abstract JavaPlugin getPlugin();

    public abstract String getId();

    public abstract void reload(Section section);

    public abstract List<Integer> getSlots();

    public abstract void setItem(MenuHolder holder);

    public abstract MenuSound getSound();

    public boolean isSlot(int slot) {
        return getSlots().contains(slot);
    }

    public void setItem(Inventory inventory, ItemStack item) {
        for (int slot : getSlots()) {
            inventory.setItem(slot, item);
        }
    }

    public void clear(Inventory inventory) {
        for (int slot : getSlots()) {
            inventory.setItem(slot, null);
        }
    }

    public ClickResult onClick(CustomMenuClickEvent e) {
        return ClickResult.NONE;
    }

    public void click(CustomMenuClickEvent e) {
        MenuHolder holder = e.getHolder();
        Player player = holder.getPlayer();

        Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), () -> {
            ClickResult result = onClick(e);
            MenuSound sound = getSound();
            if (sound != null) {
                switch (result) {
                    case SUCCESS -> sound.playSuccess(player);
                    case FAILURE -> sound.playFailure(player);
                }
            }
        });
    }

    public static MenuSound getSound(Section section) {
        if (section == null || !section.getBoolean("enabled", true)) {
            return null;
        }

        Sound success = section.contains("success")
                ? Config.requireNonNull(VersionManager.get().getSound(
                        Config.requireNonNull(ConfigUtils.parseNamespacedKey(section, "success"), section)), section)
                : null;

        Sound failure = section.contains("failure")
                ?  Config.requireNonNull(VersionManager.get().getSound(
                        Config.requireNonNull(ConfigUtils.parseNamespacedKey(section, "failure"), section)), section)
                : null;

        if (success == null && failure == null) {
            return null;
        }

        return new MenuSound(success, failure);
    }
}