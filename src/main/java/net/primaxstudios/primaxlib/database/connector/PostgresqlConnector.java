package net.primaxstudios.primaxlib.database.connector;

import com.zaxxer.hikari.HikariConfig;
import net.primaxstudios.primaxlib.database.Credentials;
import net.primaxstudios.primaxlib.database.DatabaseType;
import net.primaxstudios.primaxlib.database.PoolSettings;

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
        config.setDriverClassName("org.postgresql.Driver");
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
