package net.primaxstudios.primaxlib.item.property.type;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxlib.config.Config;
import net.primaxstudios.primaxlib.item.property.MetaProperty;
import org.bukkit.Bukkit;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.meta.BlockDataMeta;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlockDataProperty extends MetaProperty {

    public static final String ID = "block_data";
    private static final Logger logger = LoggerFactory.getLogger(BlockDataProperty.class);

    public BlockDataProperty() {
        super(logger);
    }

    @Override
    public boolean setProperty(@NotNull ItemMeta meta, @NotNull Section section) {
        String blockDataString = section.getString(ID);
        if (blockDataString == null) {
            Config.warn(logger, section, "Missing or empty '{}' value", ID);
            return false;
        }

        BlockData blockData;
        try {
            blockData = Bukkit.createBlockData(blockDataString);
        } catch (IllegalArgumentException e) {
            Config.warn(logger, section, "Invalid block data string '{}'", blockDataString);
            return false;
        }

        if (meta instanceof BlockDataMeta blockDataMeta) {
            blockDataMeta.setBlockData(blockData);
        } else if (meta instanceof BlockStateMeta blockStateMeta) {
            BlockState blockState = blockStateMeta.getBlockState();
            blockState.setBlockData(blockData);
            blockStateMeta.setBlockState(blockState);
        }else {
            Config.warn(logger, section, "Unsupported ItemMeta type for '{}'", ID);
            return false;
        }
        return true;
    }
}