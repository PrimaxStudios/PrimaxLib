package net.primaxstudios.primaxlib.menu;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.UUID;

@Getter
public class MenuHolder implements InventoryHolder {

    private final UUID uniqueId;
    private final Player player;
    private final CustomMenu customMenu;
    private final Inventory inventory;
    private final Metadata meta;

    public MenuHolder(UUID uniqueId, Player player, CustomMenu customMenu, Metadata meta) {
        this.uniqueId = uniqueId;
        this.player = player;
        this.customMenu = customMenu;
        this.inventory = customMenu.createInventory(this);
        this.meta = meta;
        customMenu.refresh(this);
    }

    public MenuHolder(UUID uniqueId, Player player, CustomMenu customMenu) {
        this(uniqueId, player, customMenu, new Metadata());
    }

    public MenuHolder(Player player, CustomMenu customMenu, Metadata meta) {
        this(UUID.randomUUID(), player, customMenu, meta);
    }

    public MenuHolder(Player player, CustomMenu customMenu) {
        this(player, customMenu, new Metadata());
    }

    public boolean isEqual(MenuHolder holder) {
        return this.uniqueId.equals(holder.getUniqueId());
    }

    public void open() {
        player.openInventory(inventory);
    }
}