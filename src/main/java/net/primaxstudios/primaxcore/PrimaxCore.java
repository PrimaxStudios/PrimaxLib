package net.primaxstudios.primaxcore;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import net.primaxstudios.primaxcore.managers.BlockManager;
import net.primaxstudios.primaxcore.configs.core.Configs;
import net.primaxstudios.primaxcore.managers.ActionManager;
import net.primaxstudios.primaxcore.managers.ItemManager;
import lombok.Getter;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import net.primaxstudios.primaxcore.managers.MenuManager;
import net.primaxstudios.primaxcore.managers.RequirementManager;
import net.primaxstudios.primaxcore.managers.MobManager;
import net.primaxstudios.primaxcore.managers.RandomizerManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class PrimaxCore extends JavaPlugin {

    public static final String NAMESPACE = "primaxcore";
    private static PrimaxCore instance;
    private Economy economy;
    private Permission permission;
    private HeadDatabaseAPI headDatabaseAPI;
    private Configs configs;
    private ActionManager actionManager;
    private RequirementManager requirementManager;
    private ItemManager itemManager;
    private RandomizerManager randomizerManager;

    @Override
    public void onEnable() {
        instance = this;

        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            economy = findEconomy();
            permission = findPermission();
        }
        if (getServer().getPluginManager().getPlugin("HeadDatabase") != null) {
            headDatabaseAPI = new HeadDatabaseAPI();
        }

        configs = new Configs();

        locale = new Locale();
        locale.reload(this);

        actionManager = new ActionManager();

        requirementManager = new RequirementManager();

        itemManager = new ItemManager();

        menuManager = new MenuManager();

        blockManager = new BlockManager();

        mobManager = new MobManager();

        randomizerManager = new RandomizerManager();

        commandManager = new CoreCommandManager();
        commandManager.register();

        getServer().getPluginManager().registerEvents(new InventoryListener(), this);

        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
            CoreEnableEvent event = new CoreEnableEvent();
            event.callEvent();
        });
    }

    public void reload() {
        configs.reload();
        locale.reload(this);
    }

    private Economy findEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return null;
        }
        return rsp.getProvider();
    }

    private Permission findPermission() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp == null) {
            return null;
        }
        return rsp.getProvider();
    }

    public static PrimaxCore inst() {
        return instance;
    }
}
