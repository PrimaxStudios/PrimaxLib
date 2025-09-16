package net.primaxstudios.primaxlib.database.connector;

import com.zaxxer.hikari.HikariConfig;
import net.primaxstudios.primaxlib.database.DatabaseType;
import net.primaxstudios.primaxlib.database.PoolSettings;

import java.io.File;

public class SqliteConnector extends SqlConnector {

    private final File file;

    public SqliteConnector(PoolSettings poolSettings, File dataFolder) {
        super(poolSettings);
        this.file = createFile(dataFolder);
    }

    public String getUrl() {
        return "jdbc:sqlite:" + file.getPath();
    }

    @Override
    public HikariConfig getConfig() {
        HikariConfig config = super.getConfig();
        config.setJdbcUrl(getUrl());
        return config;
    }

    private File createFile(File dataFolder) {
        File folder = new File(dataFolder, "databases");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return new File(folder, "database.db");
    }

    @Override
    public DatabaseType getDatabaseType() {
        return DatabaseType.SQLITE;
    }
}
