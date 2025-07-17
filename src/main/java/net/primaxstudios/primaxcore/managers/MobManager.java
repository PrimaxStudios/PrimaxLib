package net.primaxstudios.primaxcore.managers;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import net.primaxstudios.primaxcore.PrimaxCore;
import net.primaxstudios.primaxcore.listeners.MobListener;
import net.primaxstudios.primaxcore.mobs.CoreMob;
import net.primaxstudios.primaxcore.mobs.CustomMob;
import net.primaxstudios.primaxcore.mobs.ReferencedMob;
import net.primaxstudios.primaxcore.placeholders.objects.PlaceholderComponent;
import net.primaxstudios.primaxcore.registries.MobRegistry;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

@Getter
public final class MobManager {

    private final MobRegistry registry = new MobRegistry();
    private final MobFunctionManager functionManager = new MobFunctionManager();

    public MobManager() {
        Bukkit.getServer().getPluginManager().registerEvents(new MobListener(this), PrimaxCore.inst());
    }

    public CustomMob getMob(JavaPlugin plugin, Section section) {
        if (section.contains("reference")) {
            return new ReferencedMob(ConfigUtils.parseKey(section, "reference"));
        }
        CustomMob customMob = createHookMob(plugin, section);
        if (customMob == null) {
            customMob = createCoreMob(plugin, section);
        }
        if (section.contains("functions")) {
            customMob.addFunctions(functionManager.getFunctions(plugin, section.getSection("functions")));
        }
        if (customMob.containsKey()) {
            registry.register(customMob.getKey(), customMob);
        }
        return customMob;
    }

    private CoreMob createCoreMob(JavaPlugin plugin, Section section) {
        EntityType entityType = EntityType.valueOf(section.getString("type"));
        CoreMob coreMob;
        if (section.contains("id")) {
            String id = section.getString("id");
            coreMob = new CoreMob(new Key(plugin, id), entityType);
        } else {
            coreMob = new CoreMob(entityType);
        }
        if (section.contains("name")) {
            String name = section.getString("name");
            coreMob.setPlaceholderName(new PlaceholderComponent(name));
        }
        if (section.contains("drops")) {
            Section dropsSection = section.getSection("drops");
            coreMob.setCustomDropsRandomizer(PrimaxCore.inst().getRandomizerManager().getCustomItemRandomizer(plugin, dropsSection));
        }
        return coreMob;
    }

    private CustomMob createHookMob(JavaPlugin plugin, Section section) {
        return null;
    }

    public List<CustomMob> load(JavaPlugin plugin, String folder) {
        File newFolder = new File(plugin.getDataFolder() + "/" + folder);
        return getMobs(plugin, ConfigUtils.listFilesDeep(newFolder));
    }

    public CustomMob getMob(JavaPlugin plugin, File file) {
        return getMob(plugin, ConfigUtils.load(file));
    }

    public List<CustomMob> getMobs(JavaPlugin plugin, Section objectsSection) {
        return objectsSection.getRoutesAsStrings(false).stream()
                .map((route) -> getMob(plugin, objectsSection.getSection(route)))
                .toList();
    }

    public List<CustomMob> getMobs(JavaPlugin plugin, List<File> files) {
        return files.stream()
                .map(file -> getMob(plugin, file))
                .toList();
    }
}
