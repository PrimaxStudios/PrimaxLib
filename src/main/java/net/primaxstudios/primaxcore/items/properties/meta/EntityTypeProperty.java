package net.primaxstudios.primaxcore.items.properties.meta;

import net.primaxstudios.primaxcore.items.properties.ItemProperty;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class EntityTypeProperty implements ItemProperty {

    public static final String ID = "entity_type";
    private static final Logger logger = LoggerFactory.getLogger(EntityTypeProperty.class);

    @Override
    public void setProperty(@NotNull ItemStack item, @NotNull Section section) {
        Object sectionName = section.getName();
        File file = section.getRoot().getFile();

        if (!(item.getItemMeta() instanceof BlockStateMeta meta)) {
            logger.warn("Item '{}' in section '{}' of '{}' does not support BlockStateMeta. Cannot set entity type.",
                    item.getType(), sectionName, file);
            throw new IllegalArgumentException("BlockStateMeta not supported on item.");
        }

        BlockState state = meta.getBlockState();
        if (!(state instanceof CreatureSpawner spawner)) {
            logger.warn("BlockState is not a CreatureSpawner for item '{}' in section '{}' of '{}'",
                    item.getType(), sectionName, file);
            throw new IllegalArgumentException("BlockState is not a CreatureSpawner.");
        }

        EntityType type = section.getEnum(ID, EntityType.class);
        if (type == null) {
            logger.warn("Invalid or missing entity type '{}' in section '{}' of '{}'",
                    section.getString(ID), sectionName, file);
            throw new IllegalArgumentException("Invalid or missing entity type.");
        }

        spawner.setSpawnedType(type);
        meta.setBlockState(spawner);
        item.setItemMeta(meta);
    }
}