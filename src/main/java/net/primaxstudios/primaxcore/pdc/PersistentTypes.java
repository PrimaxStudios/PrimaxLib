package net.primaxstudios.primaxcore.pdc;

import net.primaxstudios.primaxcore.utils.Key;
import org.bukkit.persistence.PersistentDataType;

public interface PersistentTypes {

    PersistentDataType<String, Key> KEY = new KeyDataType();
}
