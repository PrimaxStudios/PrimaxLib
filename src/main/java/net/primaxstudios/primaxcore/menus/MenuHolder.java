package net.primaxstudios.primaxcore.menus;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.UUID;

@Getter @Setter
public class MenuHolder implements InventoryHolder {

    private final UUID uniqueId;
    private final Player player;
    private final CustomMenu customMenu;
    private final Inventory inventory;

    public MenuHolder(UUID uniqueId, Player player, CustomMenu customMenu) {
        this.uniqueId = uniqueId;
        this.player = player;
        this.customMenu = customMenu;
        this.inventory = customMenu.createInventory();
        customMenu.refresh(inventory);
    }

    public MenuHolder(Player player, CustomMenu customMenu) {
        this(UUID.randomUUID(), player, customMenu);
    }

    public boolean isEqual(MenuHolder holder) {
        return this.uniqueId.equals(holder.getUniqueId());
    }

    public void open() {
        player.openInventory(inventory);
    }
}