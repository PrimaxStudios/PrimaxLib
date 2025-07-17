package net.primaxstudios.primaxcore.managers;

import net.primaxstudios.primaxcore.PrimaxCore;
import net.primaxstudios.primaxcore.hooks.item.EcoItemsHook;
import net.primaxstudios.primaxcore.items.CoreItem;
import net.primaxstudios.primaxcore.items.CustomItem;
import net.primaxstudios.primaxcore.hooks.item.ExecutableItemsHook;
import net.primaxstudios.primaxcore.hooks.item.HeadDatabaseHook;
import net.primaxstudios.primaxcore.hooks.item.ItemsAdderHook;
import net.primaxstudios.primaxcore.items.properties.ItemProperty;
import net.primaxstudios.primaxcore.items.properties.placeholder.LoreProperty;
import net.primaxstudios.primaxcore.items.properties.placeholder.NameProperty;
import net.primaxstudios.primaxcore.listeners.ItemListener;
import net.primaxstudios.primaxcore.pdc.PersistentTypes;
import net.primaxstudios.primaxcore.registries.ItemRegistry;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import com.ssomar.score.api.executableitems.ExecutableItemsAPI;
import com.ssomar.score.api.executableitems.config.ExecutableItemInterface;
import com.willfp.ecoitems.items.EcoItem;
import com.willfp.ecoitems.items.EcoItems;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import dev.lone.itemsadder.api.CustomStack;
import lombok.Getter;
import net.primaxstudios.primaxcore.utils.Key;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

@Getter
public class ItemManager {

    private static final Logger logger = LoggerFactory.getLogger(ItemManager.class);
    private final ItemPropertyManager propertyManager = new ItemPropertyManager();
    private final ItemFunctionManager functionManager = new ItemFunctionManager();
    private final ItemRegistry registry = new ItemRegistry();

    public ItemManager() {
        Bukkit.getServer().getPluginManager().registerEvents(new ItemListener(this), PrimaxCore.inst());
    }

    public CustomItem getItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return null;
        }
        PersistentDataContainer container = meta.getPersistentDataContainer();
        Key key = container.get(PrimaxCore.IDENTIFIER_KEY, PersistentTypes.KEY);
        if (key == null) {
            return null;
        }
        return registry.getObject(key);
    }

    public CustomItem getItem(JavaPlugin plugin, Section section) {
        if (section.contains("reference")) {
            return new ReferenceItem(ConfigUtils.parseKey(section, "reference"));
        }
        CustomItem customItem = createHookItem(section);
        if (customItem == null) {
            customItem = createCoreItem(plugin, section);
        }
        if (section.contains("functions")) {
            customItem.setFunctions(functionManager.getFunctions(plugin, section.getSection("functions")));
        }
        if (customItem.containsKey()) {
            registry.register(customItem.getKey(), customItem);
        }
        return customItem;
    }

    private CoreItem createCoreItem(JavaPlugin plugin, Section section) {
        ItemStack item = propertyManager.getRegistry().getMaterialProperty().getItem(section);
        if (item == null) {
            logger.warn("Failed to create ItemStack from section '{}' of '{}'", section.getName(), section.getRoot().getFile());
            throw new RuntimeException();
        }
        CoreItem coreItem = section.contains("id")
                ? new CoreItem(new Key(plugin, section.getString("id")), item)
                : new CoreItem(item);

        for (ItemProperty property : propertyManager.getProperties(section)) {
            coreItem.setProperty(property, section);
        }
        if (section.contains(NameProperty.ID)) {
            coreItem.setNameProperty(new NameProperty(section));
        }
        if (section.contains(LoreProperty.ID)) {
            coreItem.setLoreProperty(new LoreProperty(section));
        }
        return coreItem;
    }

    private CustomItem createHookItem(Section section) {
        if (section.contains(HeadDatabaseHook.ID)) {
            String id = section.getString(HeadDatabaseHook.ID);
            ItemStack head = PrimaxCore.inst().getHeadDatabaseAPI().getItemHead(id);
            return new HeadDatabaseHook(id, head);
        }
        if (section.contains(ItemsAdderHook.ID)) {
            String id = section.getString(ItemsAdderHook.ID);
            CustomStack stack = CustomStack.getInstance(id);
            return new ItemsAdderHook(id, stack);
        }
        if (section.contains(ExecutableItemsHook.ID)) {
            String id = section.getString(ExecutableItemsHook.ID);
            ExecutableItemInterface ei = ExecutableItemsAPI.getExecutableItemsManager().getExecutableItem(id).orElse(null);
            return new ExecutableItemsHook(id, ei);
        }
        if (section.contains(EcoItemsHook.ID)) {
            String id = section.getString(EcoItemsHook.ID);
            EcoItem ecoItem = EcoItems.INSTANCE.getByID(id);
            return new EcoItemsHook(id, ecoItem);
        }
        return null;
    }

    public List<CustomItem> load(JavaPlugin plugin, String folder) {
        File newFolder = new File(plugin.getDataFolder() + "/" + folder);
        return getItems(plugin, ConfigUtils.listFilesDeep(newFolder));
    }

    public CustomItem getItem(JavaPlugin plugin, File file) {
        return getItem(plugin, ConfigUtils.load(file));
    }

    public List<CustomItem> getItems(JavaPlugin plugin, Section objectsSection) {
        return objectsSection.getRoutesAsStrings(false).stream()
                .map((route) -> getItem(plugin, objectsSection.getSection(route)))
                .toList();
    }

    public List<CustomItem> getItems(JavaPlugin plugin, List<File> files) {
        return files.stream()
                .map(file -> getItem(plugin, file))
                .toList();
    }
}