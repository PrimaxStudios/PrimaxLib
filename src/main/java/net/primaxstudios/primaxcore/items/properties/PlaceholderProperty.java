package net.primaxstudios.primaxcore.items.properties;

import net.primaxstudios.primaxcore.placeholders.Placeholder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface PlaceholderProperty {

    void setProperty(@NotNull ItemStack item, @NotNull Placeholder placeholder);
}
