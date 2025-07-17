package net.primaxstudios.primaxcore.menus;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

@Getter @Setter
public abstract class Menu implements InventoryHolder {

    private final Inventory inventory;
    private final Component title;
    private final int size;

    public Menu() {
        this.inventory = createInventory();
    }

    protected abstract Inventory createInventory();

    public void open(Player player) {
        player.openInventory(inventory);
    }
}
