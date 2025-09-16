package net.primaxstudios.primaxcore.menu.item;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import lombok.Setter;
import net.primaxstudios.primaxcore.factory.CustomItemFactory;
import net.primaxstudios.primaxcore.item.CustomItem;
import net.primaxstudios.primaxcore.menu.MenuHolder;
import net.primaxstudios.primaxcore.menu.MenuSound;
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