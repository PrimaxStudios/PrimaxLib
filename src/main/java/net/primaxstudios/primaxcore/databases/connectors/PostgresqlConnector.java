package net.primaxstudios.primaxcore.databases.connectors;

import com.zaxxer.hikari.HikariConfig;
import net.primaxstudios.primaxcore.databases.Credentials;
import net.primaxstudios.primaxcore.databases.DatabaseType;
import net.primaxstudios.primaxcore.databases.PoolSettings;

public class PostgresqlConnector extends SqlConnector {

    private final Credentials credentials;

    public PostgresqlConnector(PoolSettings settings, Credentials credentials) {
        super(settings);
        this.credentials = credentials;
    }

    public String getUrl() {
        return "jdbc:postgresql://" + credentials.getHost() + ":" + credentials.getPort() + "/" + credentials.getDatabaseName();
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
        return DatabaseType.POSTGRESQL;
    }
}
