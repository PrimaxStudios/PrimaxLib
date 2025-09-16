package net.primaxstudios.primaxlib.config;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Getter
public abstract class FolderConfig {

    private final String folderName;
    private final String[] fileNames;

    protected FolderConfig(String folderName, String... fileNames) {
        this.folderName = folderName;
        this.fileNames = fileNames;
    }

    public File getFolder(JavaPlugin plugin) {
        return new File(plugin.getDataFolder() + "/" + folderName);
    }

    public void saveFiles(JavaPlugin plugin) {
        for (String fileName : fileNames) {
            File file = new File(getFolder(plugin).toString(), fileName);
            if (!file.exists()) {
                plugin.saveResource(folderName + "/" + fileName, false);
            }
        }
    }

    public List<File> getFiles(JavaPlugin plugin) {
        saveFiles(plugin);
        File folder = getFolder(plugin);
        File[] files = folder.listFiles();
        if (files == null) {
            return null;
        }
        return Arrays.asList(files);
    }
}
