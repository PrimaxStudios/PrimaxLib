package net.primaxstudios.primaxcore.items;

import lombok.Getter;
import lombok.Setter;
import net.primaxstudios.primaxcore.utils.ItemUtils;
import net.primaxstudios.primaxcore.utils.Keyed;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Getter @Setter
public abstract class CustomItem implements Keyed {

    private final ItemStack bukkitItem;

    public CustomItem(ItemStack bukkitItem) {
        this.bukkitItem = bukkitItem;
    }

    public abstract int getAmount();

    public abstract boolean isItem(ItemStack item, boolean ignoreAmount);

    protected abstract ItemStack getItem();

    public boolean isItem(ItemStack item) {
        return isItem(item, false);
    }

    public boolean hasItem(Inventory inventory) {
        return hasItem(inventory.getContents());
    }

    public boolean hasItem(ItemStack[] items) {
        int amount = getAmount();
        int currentAmount = 0;
        for (ItemStack item : items) {
            if (item == null) {
                continue;
            }
            if (isItem(item, true)) {
                currentAmount += item.getAmount();
                if (currentAmount >= amount) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getHowMuch(Inventory inventory) {
        return getHowMuch(inventory.getContents());
    }

    public int getHowMuch(ItemStack[] items) {
        int amount = 0;
        for (ItemStack item : items) {
            if (item == null) {
                continue;
            }
            if (isItem(item, true)) {
                amount += item.getAmount();
            }
        }
        return amount;
    }

    public boolean remove(ItemStack[] items, int amount) {
        int amountRemoved = 0;
        for (int i = 0; i < items.length; i++) {
            ItemStack item = items[i];
            if (item == null) {
                continue;
            }
            if (isItem(item, true)) {
                int amountLeft = amount - amountRemoved;
                if (item.getAmount() > amountLeft) {
                    item.setAmount(item.getAmount() - amountLeft);
                    return true;
                }else {
                    amountRemoved += item.getAmount();
                    item.setAmount(0);
                    items[i] = null;
                }
            }
        }
        return false;
    }

    public boolean remove(Inventory inventory, int amount) {
        return remove(inventory.getContents(), amount);
    }

    public int remove(ItemStack[] items) {
        int amountRemoved = 0;
        for (int i = 0; i < items.length; i++) {
            ItemStack item = items[i];
            if (item == null) {
                continue;
            }
            if (isItem(item, true)) {
                amountRemoved += item.getAmount();
                item.setAmount(0);
                items[i] = null;
            }
        }
        return amountRemoved;
    }

    public int remove(Inventory inventory) {
        return remove(inventory.getContents());
    }

    public void give(Player player, int amount, boolean overflow) {
        ItemUtils.give(player, getItem(), amount, overflow);
    }

    public void give(Player player, boolean overflow) {
        ItemUtils.give(player, getItem(), overflow);
    }

    public void drop(Location location, int amount) {
        ItemUtils.drop(location, getItem(), amount);
    }

    public void drop(Location location) {
        ItemUtils.drop(location, getItem());
    }

    public void drop(Player player, int amount) {
        ItemUtils.drop(player.getLocation(), getItem(), amount);
    }

    public void drop(Player player) {
        ItemUtils.drop(player.getLocation(), getItem());
    }
}