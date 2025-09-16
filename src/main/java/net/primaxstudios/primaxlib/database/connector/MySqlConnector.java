package net.primaxstudios.primaxlib.database.connector;

import com.zaxxer.hikari.HikariConfig;
import lombok.Getter;
import lombok.Setter;
import net.primaxstudios.primaxlib.database.Credentials;
import net.primaxstudios.primaxlib.database.DatabaseType;
import net.primaxstudios.primaxlib.database.PoolSettings;

@Getter @Setter
public class MySqlConnector extends SqlConnector {

    private final Credentials credentials;

    public MySqlConnector(PoolSettings poolSettings, Credentials credentials) {
        super(poolSettings);
        this.credentials = credentials;
    }

    public String getUrl() {
        return "jdbc:mysql://" + credentials.getHost() + ":" + credentials.getPort() + "/" + credentials.getDatabaseName();
    }

    @Override
    public HikariConfig getConfig() {
        HikariConfig config = super.getConfig();
        config.setJdbcUrl(getUrl());
        config.setUsername(credentials.getUsername());
        if (credentials.getPassword() != null) {
            config.setPassword(credentials.getPassword());
        }
        return config;
    }

    @Override
    public DatabaseType getDatabaseType() {
        return DatabaseType.MYSQL;
    }
}
