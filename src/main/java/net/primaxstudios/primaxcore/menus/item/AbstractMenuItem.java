package net.primaxstudios.primaxcore.menus.item;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import lombok.Setter;
import net.primaxstudios.primaxcore.PrimaxCore;
import net.primaxstudios.primaxcore.items.CustomItem;
import net.primaxstudios.primaxcore.menus.MenuHolder;

@Getter @Setter
public abstract class AbstractMenuItem implements MenuItem {

    private CustomItem customItem;

    public void reload(Section section) {
        customItem = PrimaxCore.inst().getItemManager().getItem(section);
    }

    @Override
    public void setItem(MenuHolder holder) {
        setItem(holder.getInventory(), customItem.getItem());
    }
}