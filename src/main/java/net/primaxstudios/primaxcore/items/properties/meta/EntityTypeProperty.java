package net.primaxstudios.primaxcore.items.properties.meta;

import net.primaxstudios.primaxcore.configs.Config;
import net.primaxstudios.primaxcore.items.properties.AdvancedMetaProperty;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityTypeProperty extends AdvancedMetaProperty<BlockStateMeta> {

    public static final String ID = "entity_type";
    private static final Logger logger = LoggerFactory.getLogger(EntityTypeProperty.class);

    public EntityTypeProperty() {
        super(logger, BlockStateMeta.class);
    }

    @Override
    public void setProperty(@NotNull BlockStateMeta meta, @NotNull Section section) {
        BlockState state = meta.getBlockState();
        if (!(state instanceof CreatureSpawner spawner)) {
            Config.warn(logger, section, "BlockState is not a CreatureSpawner. Cannot set entity type.");
            return;
        }

        EntityType type = ConfigUtils.parseEnum(section, ID, EntityType.class);
        if (type == null) return;

        spawner.setSpawnedType(type);
        meta.setBlockState(spawner);
    }
}