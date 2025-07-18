package net.primaxstudios.primaxcore.hooks;

import net.primaxstudios.primaxcore.items.CustomItem;
import com.willfp.ecoitems.items.EcoItem;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
public class EcoItemsHook extends CustomItem {

    public static final String ID = "ecoitems";
    private final String key;
    private final EcoItem ecoItem;

    public EcoItemsHook(String key, EcoItem ecoItem) {
        this.key = key;
        this.ecoItem = ecoItem;
    }

    @Override
    public boolean isItem(ItemStack item, boolean ignoreAmount) {
//        if (ignoreAmount) {
//            return ecoItem.getItemStack().isSimilar(item);
//        }else {
//            return ecoItem.getItemStack().equals(item);
//        }
        return true;
    }

    @Override
    public int getAmount() {
//        return ecoItem.getItemStack().getAmount();
        return 1;
    }

    @Override
    public ItemStack getItem() {
//        return ecoItem.getItemStack();
        return null;
    }
}