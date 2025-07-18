package net.primaxstudios.primaxcore.managers;

import net.primaxstudios.primaxcore.PrimaxCore;
import net.primaxstudios.primaxcore.hooks.EcoItemsHook;
import net.primaxstudios.primaxcore.items.CoreItem;
import net.primaxstudios.primaxcore.items.CustomItem;
import net.primaxstudios.primaxcore.hooks.ExecutableItemsHook;
import net.primaxstudios.primaxcore.hooks.HeadDatabaseHook;
import net.primaxstudios.primaxcore.hooks.ItemsAdderHook;
import net.primaxstudios.primaxcore.items.properties.ItemProperty;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import com.ssomar.score.api.executableitems.ExecutableItemsAPI;
import com.ssomar.score.api.executableitems.config.ExecutableItemInterface;
import com.willfp.ecoitems.items.EcoItem;
import com.willfp.ecoitems.items.EcoItems;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import dev.lone.itemsadder.api.CustomStack;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

@Getter
public class ItemManager {

    private static final Logger logger = LoggerFactory.getLogger(ItemManager.class);
    private final ItemPropertyManager propertyManager = new ItemPropertyManager();

    public String getKey(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return null;
        }
        PersistentDataContainer container = meta.getPersistentDataContainer();
        return container.get(PrimaxCore.IDENTIFIER_KEY, PersistentDataType.STRING);
    }

    public CustomItem getItem(Section section) {
        CustomItem hookItem = createHookItem(section);
        if (hookItem != null) {
            return hookItem;
        }
        return createCoreItem(section);
    }

    private CoreItem createCoreItem(Section section) {
        ItemStack item = propertyManager.getRegistry().getMaterialProperty().getItem(section);
        if (item == null) {
            logger.warn("Failed to create ItemStack from section '{}' of '{}'", section.getName(), section.getRoot().getFile());
            throw new RuntimeException();
        }
        CoreItem coreItem = section.contains("id")
                ? new CoreItem(section.getString("id"), item)
                : new CoreItem(item);

        for (ItemProperty property : propertyManager.getProperties(section)) {
            coreItem.setProperty(property, section);
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
        return getItems(ConfigUtils.listFilesDeep(newFolder));
    }

    public CustomItem getItem(File file) {
        return getItem(ConfigUtils.load(file));
    }

    public List<CustomItem> getItems(Section objectsSection) {
        return objectsSection.getRoutesAsStrings(false).stream()
                .map((route) -> getItem(objectsSection.getSection(route)))
                .toList();
    }

    public List<CustomItem> getItems(List<File> files) {
        return files.stream()
                .map(this::getItem)
                .toList();
    }
}