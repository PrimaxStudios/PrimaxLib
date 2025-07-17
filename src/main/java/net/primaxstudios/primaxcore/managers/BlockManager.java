package net.primaxstudios.primaxcore.managers;

import com.jeff_media.customblockdata.CustomBlockData;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import net.primaxstudios.primaxcore.PrimaxCore;
import net.primaxstudios.primaxcore.blocks.CoreBlock;
import net.primaxstudios.primaxcore.blocks.CustomBlock;
import net.primaxstudios.primaxcore.blocks.ReferencedBlock;
import net.primaxstudios.primaxcore.hooks.block.ItemsAdderHook;
import net.primaxstudios.primaxcore.listeners.BlockListener;
import net.primaxstudios.primaxcore.pdc.PersistentTypes;
import net.primaxstudios.primaxcore.registries.BlockRegistry;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import net.primaxstudios.primaxcore.utils.Key;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

@Getter
public class BlockManager {

    private final BlockRegistry registry = new BlockRegistry();

    public BlockManager() {
        Bukkit.getServer().getPluginManager().registerEvents(new BlockListener(this), PrimaxCore.inst());
    }

    public CustomBlock getBlock(Block block) {
        CustomBlockData container = new CustomBlockData(block, PrimaxCore.inst());
        Key key = container.get(PrimaxCore.IDENTIFIER_KEY, PersistentTypes.KEY);
        if (key == null) {
            return null;
        }
        return registry.getObject(key);
    }

    public CustomBlock getBlock(JavaPlugin plugin, Section section) {
        if (section.contains("reference")) {
            return new ReferencedBlock(ConfigUtils.parseKey(section, "reference"));
        }
        CustomBlock addonBlock = createAddonBlock(section);
        if (addonBlock != null) {
            return addonBlock;
        }
        CoreBlock coreBlock = createCoreBlock(plugin, section);
        if (coreBlock.containsKey()) {
            registry.register(coreBlock.getKey(), coreBlock);
        }
        return coreBlock;
    }

    private CoreBlock createCoreBlock(JavaPlugin plugin, Section section) {
        Material material = Material.getMaterial(section.getString("material"));
        CoreBlock coreBlock;
        if (section.contains("id")) {
            String id = section.getString("id");
            coreBlock = new CoreBlock(new Key(plugin, id), material);
        } else {
            coreBlock = new CoreBlock(material);
        }
        if (section.contains("item")) {
            Section itemSection = section.getSection("item");
            coreBlock.setCustomItem(PrimaxCore.inst().getItemManager().getItem(plugin, itemSection));
        }
        if (section.contains("drops")) {
            Section dropsSection = section.getSection("drops");
            coreBlock.setCustomDropsRandomizer(PrimaxCore.inst().getRandomizerManager().getCustomItemRandomizer(plugin, dropsSection));
        }
        return coreBlock;
    }

    private CustomBlock createAddonBlock(Section section) {
        if (section.contains(ItemsAdderHook.ID)) {
            String id = section.getString(ItemsAdderHook.ID);
            dev.lone.itemsadder.api.CustomBlock block = dev.lone.itemsadder.api.CustomBlock.getInstance(id);
            return new ItemsAdderHook(id, block);
        }
        return null;
    }

    public List<CustomBlock> load(JavaPlugin plugin, String folder) {
        File newFolder = new File(plugin.getDataFolder() + "/" + folder);
        return getBlocks(plugin, ConfigUtils.listFilesDeep(newFolder));
    }

    public CustomBlock getBlock(JavaPlugin plugin, File file) {
        return getBlock(plugin, ConfigUtils.load(file));
    }

    public List<CustomBlock> getBlocks(JavaPlugin plugin, Section objectsSection) {
        return objectsSection.getRoutesAsStrings(false).stream()
                .map((route) -> getBlock(plugin, objectsSection.getSection(route)))
                .toList();
    }

    public List<CustomBlock> getBlocks(JavaPlugin plugin, List<File> files) {
        return files.stream().map(file -> getBlock(plugin, file)).toList();
    }
}