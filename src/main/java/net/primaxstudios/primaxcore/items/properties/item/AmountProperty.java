package net.primaxstudios.primaxcore.items.properties.item;

import net.primaxstudios.primaxcore.items.properties.ItemProperty;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class AmountProperty implements ItemProperty {

    public static final String ID = "amount";
    private static final Logger logger = LoggerFactory.getLogger(AmountProperty.class);

    @Override
    public void setProperty(@NotNull ItemStack item, @NotNull Section section) {
        File file = section.getRoot().getFile();
        Object sectionName = section.getName();

        int amount = section.getInt(ID, -1);
        if (amount <= 0 || amount > item.getMaxStackSize()) {
            logger.warn("Invalid amount '{}' for item '{}' in section '{}' of '{}'. Must be between 1 and {}",
                    amount, item.getType(), sectionName, file, item.getMaxStackSize());
            return;
        }

        item.setAmount(amount);
    }
}