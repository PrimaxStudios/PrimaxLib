package net.primaxstudios.primaxcore.hooks;

import net.primaxstudios.primaxcore.items.CustomItem;
import dev.lone.itemsadder.api.CustomStack;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
public class ItemsAdderHook extends CustomItem {

    public static final String ID = "itemsadder";
    private final String key;
    private final CustomStack stack;

    public ItemsAdderHook(String key, CustomStack stack) {
        this.key = key;
        this.stack = stack;
    }

    @Override
    public boolean isItem(ItemStack item, boolean ignoreAmount) {
        CustomStack newStack = CustomStack.byItemStack(item);
        if (newStack == null || !newStack.getNamespacedID().equals(stack.getNamespacedID())) {
            return false;
        }
        return ignoreAmount || newStack.getItemStack().getAmount() == getAmount();
    }

    @Override
    public int getAmount() {
        return stack.getItemStack().getAmount();
    }

    @Override
    public ItemStack getItem() {
        return stack.getItemStack();
    }
}