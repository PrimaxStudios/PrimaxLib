package net.primaxstudios.primaxcore.database.connector;

import com.zaxxer.hikari.HikariConfig;
import net.primaxstudios.primaxcore.database.Credentials;
import net.primaxstudios.primaxcore.database.DatabaseType;
import net.primaxstudios.primaxcore.database.PoolSettings;

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
        config.setDriverClassName("net.primaxstudios.primaxcore.libs.postgresql.Driver");
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
