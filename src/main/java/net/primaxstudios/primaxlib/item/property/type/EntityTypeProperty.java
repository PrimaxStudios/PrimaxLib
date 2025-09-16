package net.primaxstudios.primaxlib.item.property.type;

import net.primaxstudios.primaxlib.configuration.Config;
import net.primaxstudios.primaxlib.item.property.SpecificMetaProperty;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxlib.util.ConfigUtils;
import net.primaxstudios.primaxlib.version.VersionManager;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityTypeProperty extends SpecificMetaProperty<BlockStateMeta> {

    public static final String ID = "entity_type";
    private static final Logger logger = LoggerFactory.getLogger(EntityTypeProperty.class);

    public EntityTypeProperty() {
        super(logger, BlockStateMeta.class);
    }

    @Override
    public boolean setProperty(@NotNull BlockStateMeta meta, @NotNull Section section) {
        BlockState state = meta.getBlockState();
        if (!(state instanceof CreatureSpawner spawner)) {
            Config.warn(logger, section, "BlockState is not a CreatureSpawner. Cannot set entity type.");
            return false;
        }

        NamespacedKey key = ConfigUtils.parseNamespacedKey(section, ID);
        if (key == null) return false;

        EntityType type = VersionManager.get().getEntityType(key);
        if (type == null) {
            Config.warn(logger, section, "Invalid entity type key '{}'", key);
            return false;
        }

        spawner.setSpawnedType(type);
        meta.setBlockState(spawner);
        return true;
    }
}