package net.primaxstudios.primaxlib.menu.item;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import lombok.Setter;
import net.primaxstudios.primaxlib.factory.CustomItemFactory;
import net.primaxstudios.primaxlib.item.CustomItem;
import net.primaxstudios.primaxlib.menu.MenuHolder;
import net.primaxstudios.primaxlib.menu.MenuSound;
import org.bukkit.plugin.java.JavaPlugin;

@Getter @Setter
public abstract class AbstractMenuItem extends MenuItem {

    private CustomItem customItem;
    private MenuSound sound;

    public abstract JavaPlugin getPlugin();

    public void reload(Section section) {
        customItem = CustomItemFactory.fromSection(section);
        sound = MenuItem.getSound(section.getSection("sound"));
    }

    @Override
    public void setItem(MenuHolder holder) {
        setItem(holder.getInventory(), customItem.getItem());
    }
}