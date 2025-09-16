package net.primaxstudios.primaxlib.item;

import lombok.Getter;
import net.primaxstudios.primaxlib.util.ItemUtils;
import org.bukkit.inventory.ItemStack;

@Getter
public class DataItem extends CustomItem {

    private final String data;
    private final ItemStack item;

    public DataItem(String data) {
        this.data = data;
        this.item = ItemUtils.deserializeFromString(data);
    }

    @Override
    public boolean isItem(ItemStack item, boolean ignoreAmount) {
        if (ignoreAmount) {
            return getItem().isSimilar(item);
        } else {
            return getItem().equals(item);
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
