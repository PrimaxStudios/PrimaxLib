package net.primaxstudios.primaxcore.configs;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import net.primaxstudios.primaxcore.utils.ConfigUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class Config {

    private final Map<Class<? extends Settings>, Settings> settingsByClass = new HashMap<>();

    public Config(Settings... settingsList) {
        for (Settings settings : settingsList) {
            settingsByClass.put(settings.getClass(), settings);
        }
    }

    public abstract File getFile();

    public void reload() {
        File file = getFile();
        YamlDocument document = ConfigUtils.load(file);

        for (Settings settings : settingsByClass.values()) {
            Section section = document.getSection(settings.getPath());
            if (section == null) return;

            settings.reload(section);
        }
    }

    public <T extends Settings> T getSettings(Class<T> aClass) {
        return (T) settingsByClass.get(aClass);
    }
}
