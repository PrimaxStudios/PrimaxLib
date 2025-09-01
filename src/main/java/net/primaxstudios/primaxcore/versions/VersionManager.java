package net.primaxstudios.primaxcore.versions;

import org.bukkit.Bukkit;

public class VersionManager {

    private static final VersionAdapter ADAPTER;

    static {
        String ver = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        switch (ver) {
            case "v1_19_R1", "v1_19_R2", "v1_19_R3", "v1_19_R4", "v1_20_R1", "v1_20_R2", "v1_20_R3", "v1_20_R4",
                 "v1_20_R5", "v1_20_R6" -> ADAPTER = new VersionAdapter_1_20();
            case "v1_21_R1", "v1_21_R2", "v1_21_R3", "v1_21_R4", "v1_21_R5", "v1_21_R6", "v1_21_R7", "v1_21_R8" -> ADAPTER = new VersionAdapter_1_21();
            default -> throw new IllegalStateException("Unsupported version: " + ver);
        }
    }

    public static VersionAdapter get() {
        return ADAPTER;
    }
}
