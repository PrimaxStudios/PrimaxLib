package net.primaxstudios.primaxcore.items;

import net.primaxstudios.primaxcore.PrimaxCore;
import net.primaxstudios.primaxcore.items.properties.ItemProperty;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

@Getter
public class CoreItem extends CustomItem {

    private final String key;
    private final ItemStack bukkitItem;

    public CoreItem(String key, ItemStack bukkitItem) {
        this.key = key;
        this.bukkitItem = bukkitItem;
        if (key != null) {
            attachIdentifier();
        }
    }

    public CoreItem(ItemStack bukkitItem) {
        this(null, bukkitItem);
    }

    private void attachIdentifier() {
        if (key == null) {
            return;
        }
        ItemMeta meta = bukkitItem.getItemMeta();
        if (meta == null) {
            return;
        }
        meta.getPersistentDataContainer().set(PrimaxCore.IDENTIFIER_KEY, PersistentDataType.STRING, key);
        bukkitItem.setItemMeta(meta);
    }

    private boolean isIdentified(ItemStack item, boolean ignoreAmount) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }
        String key = meta.getPersistentDataContainer().get(PrimaxCore.IDENTIFIER_KEY, PersistentDataType.STRING);
        if (!Objects.equals(this.key, key)) {
            return false;
        }
        return ignoreAmount || item.getAmount() == getAmount();
    }

    @Override
    public boolean isItem(ItemStack item, boolean ignoreAmount) {
        if (key != null) {
            return isIdentified(item, ignoreAmount);
        }
        if (ignoreAmount) {
            return getItem().isSimilar(item);
        } else {
            return getItem().equals(item);
        }
    }

    @Override
    public int getAmount() {
        return Math.max(bukkitItem.getAmount(), 1);
    }

    @Override
    public ItemStack getItem() {
        return bukkitItem.clone();
    }

    public void setProperty(ItemProperty property, Section section) {
        property.setProperty(bukkitItem, section);
    }
}