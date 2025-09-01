package net.primaxstudios.primaxcore.dependency;

import com.alessiodp.libby.BukkitLibraryManager;
import com.alessiodp.libby.Library;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class DependencyManager extends BukkitLibraryManager {

    public DependencyManager(@NotNull JavaPlugin plugin) {
        super(plugin);
    }

    /**
     * Subclasses must declare their required dependencies.
     */
    protected abstract List<Library> declareLibraries();

    /**
     * Load and resolve all declared dependencies.
     */
    public void loadLibraries() {
        for (Library lib : declareLibraries()) {
            loadLibrary(lib);
        }
    }
}