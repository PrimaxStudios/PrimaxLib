package net.primaxstudios.primaxcore.pdc;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

public interface PersistentTypes {

    PersistentDataType<String, NamespacedKey> NAMESPACEDKEY = new NamespacedKeyDataType();
}
