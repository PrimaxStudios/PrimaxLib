package net.primaxstudios.primaxcore.item.property;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface ItemProperty {

    boolean setProperty(@NotNull ItemStack item, @NotNull Section section);
}
