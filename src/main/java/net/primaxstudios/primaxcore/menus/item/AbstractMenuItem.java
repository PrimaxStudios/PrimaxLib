package net.primaxstudios.primaxcore.menus.item;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import lombok.Setter;
import net.primaxstudios.primaxcore.PrimaxCore;
import net.primaxstudios.primaxcore.items.CustomItem;
import net.primaxstudios.primaxcore.menus.MenuHolder;
import net.primaxstudios.primaxcore.menus.MenuSound;
import org.bukkit.plugin.java.JavaPlugin;

@Getter @Setter
public abstract class AbstractMenuItem extends MenuItem {

    private CustomItem customItem;
    private MenuSound sound;

    public abstract JavaPlugin getPlugin();

    public void reload(Section section) {
        customItem = PrimaxCore.inst().getItemManager().getItem(getPlugin(), section);
        sound = MenuItem.getSound(section.getSection("sound"));
    }

    @Override
    public void setItem(MenuHolder holder) {
        setItem(holder.getInventory(), customItem.getItem());
    }
}