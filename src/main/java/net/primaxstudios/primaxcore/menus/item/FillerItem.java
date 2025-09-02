package net.primaxstudios.primaxcore.menus.item;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import lombok.Setter;
import net.primaxstudios.primaxcore.menus.item.slot.SlotBoundItem;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

@Getter @Setter
public class FillerItem extends AbstractMenuItem implements SlotBoundItem, OptionalItem {

    private final JavaPlugin plugin;
    private boolean enabled;
    private List<Integer> slots;

    public FillerItem(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getId() {
        return "filler";
    }

    @Override
    public void reload(Section section) {
        enabled = section.getBoolean("enabled", true);
        if (!enabled) return;

        super.reload(section);
        if (section.contains("slots")) {
            slots = ConfigUtils.parseSlots(section, "slots");
        }
    }

    @Override
    public void setItem(Inventory inventory, ItemStack item) {
        if (slots == null || slots.isEmpty()) {
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, item);
            }
        }else {
            slots.forEach(slot -> inventory.setItem(slot, item));
        }
    }

    @Override
    public void clear(Inventory inventory) {
        if (slots == null || slots.isEmpty()) {
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, null);
            }
        } else {
            slots.forEach(slot -> inventory.setItem(slot, null));
        }
    }
}
