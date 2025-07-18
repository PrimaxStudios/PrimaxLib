package net.primaxstudios.primaxcore.hooks;

import net.primaxstudios.primaxcore.items.CustomItem;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
public class HeadDatabaseHook extends CustomItem {

    public static final String ID = "headdb";
    private final String key;
    private final ItemStack item;

    public HeadDatabaseHook(String key, ItemStack item) {
        this.key = key;
        this.item = item;
    }

    @Override
    public boolean isItem(ItemStack item, boolean ignoreAmount) {
        if (ignoreAmount) {
            return this.item.isSimilar(item);
        }else {
            return this.item.equals(item);
        }
    }

    @Override
    public int getAmount() {
        return item.getAmount();
    }

    @Override
    public ItemStack getItem() {
        return item.clone();
    }
}
