package net.primaxstudios.primaxcore.items;

import net.primaxstudios.primaxcore.PrimaxCore;
import net.primaxstudios.primaxcore.items.properties.ItemProperty;
import net.primaxstudios.primaxcore.items.properties.placeholder.LoreProperty;
import net.primaxstudios.primaxcore.items.properties.placeholder.NameProperty;
import net.primaxstudios.primaxcore.pdc.PersistentTypes;
import net.primaxstudios.primaxcore.placeholders.Placeholder;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

@Getter @Setter
public class CoreItem extends CustomItem  {

    private final Key key;
    private final ItemStack bukkitItem;
    private NameProperty nameProperty;
    private LoreProperty loreProperty;

    public CoreItem(Key key, ItemStack bukkitItem) {
        this.key = key;
        this.bukkitItem = bukkitItem;
        attachIdentifier();
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
        meta.getPersistentDataContainer().set(PrimaxCore.IDENTIFIER_KEY, PersistentTypes.KEY, key);
        bukkitItem.setItemMeta(meta);
    }

    private boolean isIdentified(ItemStack item, boolean ignoreAmount) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }
        Key key = meta.getPersistentDataContainer().get(PrimaxCore.IDENTIFIER_KEY, PersistentTypes.KEY);
        if (!Objects.equals(this.key, key)) {
            return false;
        }
        return ignoreAmount || item.getAmount() == getAmount();
    }

    @Override
    public int getAmount() {
        return Math.max(bukkitItem.getAmount(), 1);
    }

    @Override
    public boolean isItem(ItemStack item, boolean ignoreAmount) {
        if (key != null) {
            return isIdentified(item, ignoreAmount);
        }
        if (ignoreAmount) {
            return getItem().isSimilar(item);
        }else {
            return getItem().equals(item);
        }
    }

    @Override
    protected ItemStack getItemAbstract(Placeholder placeholder) {
        ItemStack item = bukkitItem.clone();
        if (nameProperty != null) {
            nameProperty.setProperty(item, placeholder);
        }
        if (loreProperty != null) {
            loreProperty.setProperty(item, placeholder);
        }
        return item;
    }

    public void setProperty(ItemProperty property, Section section) {
        property.setProperty(bukkitItem, section);
    }
}
