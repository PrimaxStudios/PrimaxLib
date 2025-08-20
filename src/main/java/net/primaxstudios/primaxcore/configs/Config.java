package net.primaxstudios.primaxcore.configs;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public abstract class Config {

    private final Map<Class<? extends Settings>, Settings> settingsByClass;

    public Config(Settings... settingsList) {
        this.settingsByClass = Arrays.stream(settingsList).collect(Collectors.toMap(Settings::getClass, settings -> settings));
    }

    public abstract YamlDocument getDocument() throws IOException;

    public void reload() {
        try {
            YamlDocument document = getDocument();

            for (Settings settings : settingsByClass.values()) {
                String path = settings.getPath();
                if (path != null) {
                    Section section = Config.requireNonNull(document.getSection(path), document);
                    settings.reload(section);
                }else {
                    settings.reload(document);
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @SuppressWarnings("unchecked")
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
        File file = section.getRoot().getFile();

        String filePath = file != null ? file.getAbsolutePath() : "Unknown File";
        String normalizedPath = filePath.replace(File.separatorChar, '/');

        return formatMessage(" in section '{}' of file '{}'", sectionName, normalizedPath);
    }

    public static @NotNull <T> T requireNonNull(T object, Section section, String message, Object... objects) {
        return Objects.requireNonNull(object, formatMessage(message, objects) + createCause(section));
    }

    public static @NotNull <T> T requireNonNull(T object, Section section) {
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
