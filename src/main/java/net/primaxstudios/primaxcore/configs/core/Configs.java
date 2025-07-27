package net.primaxstudios.primaxcore.configs.core;

import dev.dejvokep.boostedyaml.YamlDocument;
import lombok.Getter;
import net.primaxstudios.primaxcore.PrimaxCore;
import net.primaxstudios.primaxcore.utils.ConfigUtils;

import java.io.IOException;

@Getter
public class Configs {

    private final YamlDocument config;

    public Configs() {
        try {
            this.config = ConfigUtils.loadDefault(PrimaxCore.inst(), "config.yml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ConfigUtils.saveDefaults(PrimaxCore.inst(), "locale", "en.yml");
    }

    public void reload() {
        try {
            config.reload();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
