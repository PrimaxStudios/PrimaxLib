package net.primaxstudios.primaxcore.configs;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import net.primaxstudios.primaxcore.utils.ConfigUtils;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        try {
            File file = getFile();

            YamlDocument document = ConfigUtils.load(file);

            for (Settings settings : settingsByClass.values()) {
                Section section = document.getSection(settings.getPath());
                if (section == null) return;

                settings.reload(section);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public <T extends Settings> T getSettings(Class<T> aClass) {
        return (T) settingsByClass.get(aClass);
    }

    private static String formatMessage(String message, Object... args) {
        for (Object arg : args) {
            message = message.replaceFirst("\\{}", arg == null ? "null" : arg.toString());
        }
        return message;
    }

    private static String createCause(Section section) {
        Object sectionName = section.getName();
        File filePath = section.getRoot().getFile();

        return formatMessage("in section '{}' of file '{}'", sectionName, filePath);
//        return " in section '" + sectionName + "' of file '" + filePath;
    }

    public static <T> T requireNonNull(T object, Section section, String message, Object... objects) {
        return Objects.requireNonNull(object, formatMessage(message, objects) + createCause(section));
    }

    public static <T> T requireNonNull(T object, Section section) {
        return Objects.requireNonNull(object, createCause(section));
    }

    public static void info(Logger logger, Section section, String message, Object... objects) {
        String string = message + createCause(section);
        logger.info(string, objects);
    }

    public static void warn(Logger logger, Section section, String message, Object... objects) {
        String string = message + createCause(section);
        logger.warn(string, objects);
    }

    public static void error(Logger logger, Section section, String message, Object... objects) {
        String string = message + createCause(section);
        logger.error(string, objects);
    }
}
