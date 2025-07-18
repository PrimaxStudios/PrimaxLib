package net.primaxstudios.primaxcore.hooks;

import net.primaxstudios.primaxcore.items.CustomItem;
import com.ssomar.score.api.executableitems.ExecutableItemsAPI;
import com.ssomar.score.api.executableitems.config.ExecutableItemInterface;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

@Getter
public class ExecutableItemsHook extends CustomItem {

    public static final String ID = "executableitems";
    private final String key;
    private final ExecutableItemInterface executableItem;

    public ExecutableItemsHook(String key, ExecutableItemInterface executableItem) {
        this.key = key;
        this.executableItem = executableItem;
    }

    @Override
    public boolean isItem(ItemStack item, boolean ignoreAmount) {
        ExecutableItemInterface newExecutableItem = ExecutableItemsAPI.getExecutableItemsManager().getExecutableItem(item).orElse(null);
        if (newExecutableItem == null || !newExecutableItem.getId().equals(executableItem.getId())) {
            return false;
        }
        return ignoreAmount || item.getAmount() == getAmount();
    }

    @Override
    public int getAmount() {
        return 1;
    }

    @Override
    public ItemStack getItem() {
        return executableItem.buildItem(getAmount(), Optional.empty());
    }
}
