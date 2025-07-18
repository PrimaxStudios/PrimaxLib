package net.primaxstudios.primaxcore.items.properties.meta;

import net.primaxstudios.primaxcore.items.properties.ItemProperty;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.Bukkit;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockDataMeta;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class BlockDataProperty implements ItemProperty {

    public static final String ID = "block_data";
    private static final Logger logger = LoggerFactory.getLogger(BlockDataProperty.class);

    @Override
    public void setProperty(@NotNull ItemStack item, @NotNull Section section) {
        Object sectionName = section.getName();
        File file = section.getRoot().getFile();

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            logger.warn("ItemMeta is null for item '{}' in section '{}' of '{}'", item.getType(), sectionName, file);
            throw new IllegalArgumentException("ItemMeta is null.");
        }

        String blockDataString = section.getString(ID);
        if (blockDataString == null || blockDataString.isEmpty()) {
            logger.warn("Missing or empty '{}' value in section '{}' of '{}'", ID, sectionName, file);
            throw new IllegalArgumentException("Block-data string is missing or empty.");
        }

        BlockData blockData;
        try {
            blockData = Bukkit.createBlockData(blockDataString);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid block data string '{}' in section '{}' of '{}'", blockDataString, sectionName, file, e);
            throw new IllegalArgumentException("Invalid block data string: " + blockDataString, e);
        }

        boolean success = false;

        if (meta instanceof BlockDataMeta blockDataMeta) {
            blockDataMeta.setBlockData(blockData);
            success = true;
        } else if (meta instanceof BlockStateMeta blockStateMeta) {
            BlockState blockState = blockStateMeta.getBlockState();
            blockState.setBlockData(blockData);
            blockStateMeta.setBlockState(blockState);
            success = true;
        }

        if (!success) {
            logger.warn("ItemMeta of type '{}' does not support block-data in section '{}' of '{}'",
                    meta.getClass().getSimpleName(), sectionName, file);
            throw new IllegalArgumentException("Unsupported ItemMeta type for block-data.");
        }

        item.setItemMeta(meta);
    }
}