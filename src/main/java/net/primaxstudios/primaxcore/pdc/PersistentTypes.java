package net.primaxstudios.primaxcore.pdc;

import org.bukkit.persistence.PersistentDataType;

public interface PersistentTypes {

    PersistentDataType<String, Key> KEY = new KeyDataType();
}
