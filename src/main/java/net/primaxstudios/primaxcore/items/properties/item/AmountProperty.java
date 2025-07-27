package net.primaxstudios.primaxcore.items.properties.item;

import net.primaxstudios.primaxcore.configs.Config;
import net.primaxstudios.primaxcore.items.properties.ItemProperty;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmountProperty implements ItemProperty {

    public static final String ID = "amount";
    private static final Logger logger = LoggerFactory.getLogger(AmountProperty.class);

    @Override
    public void setProperty(@NotNull ItemStack item, @NotNull Section section) {
        int amount = section.getInt(ID, -1);
        if (amount <= 0 || amount > item.getMaxStackSize()) {
            Config.warn(logger, section, "Invalid amount '{}' for item '{}'", amount, item.getType());
            return;
        }

        item.setAmount(amount);
    }
}