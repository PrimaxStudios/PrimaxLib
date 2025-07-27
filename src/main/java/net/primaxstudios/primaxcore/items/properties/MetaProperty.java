package net.primaxstudios.primaxcore.items.properties;

import org.bukkit.inventory.meta.ItemMeta;
import org.slf4j.Logger;

public abstract class MetaProperty extends AdvancedMetaProperty<ItemMeta> {

    public MetaProperty(Logger logger) {
        super(logger, ItemMeta.class);
    }
}
