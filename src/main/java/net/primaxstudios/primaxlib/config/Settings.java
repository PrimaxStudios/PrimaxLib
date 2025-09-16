package net.primaxstudios.primaxlib.config;

import dev.dejvokep.boostedyaml.block.implementation.Section;

public interface Settings {

    String getPath();

    void reload(Section section);
}
