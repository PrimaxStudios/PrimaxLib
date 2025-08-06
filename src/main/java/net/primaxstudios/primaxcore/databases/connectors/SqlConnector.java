package net.primaxstudios.primaxcore.databases.connectors;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;
import net.primaxstudios.primaxcore.databases.ConnectorType;
import net.primaxstudios.primaxcore.databases.DatabaseConnector;
import net.primaxstudios.primaxcore.databases.PoolSettings;
import net.primaxstudios.primaxcore.databases.processors.ResultExecutor;
import net.primaxstudios.primaxcore.databases.processors.ResultQuery;
import org.jetbrains.annotations.Blocking;

import java.sql.*;

@Getter @Setter
public abstract class SqlConnector implements DatabaseConnector {

    private final PoolSettings settings;
    private HikariDataSource source;

    protected SqlConnector(PoolSettings settings) {
        this.settings = settings;
    }

    @Override
    public ConnectorType getConnectorType() {
        return ConnectorType.SQL;
    }

    @Override
    public void connect() {
        this.source = new HikariDataSource(getConfig());
    }

    public HikariConfig getConfig() {
        HikariConfig config = new HikariConfig();
        config.setMaximumPoolSize(settings.getMaximumPoolSize());  // Increase the max pool size (default is 10)
        config.setMinimumIdle(settings.getMinimumIdle());       // Maintain some idle connections
        config.setMaxLifetime(settings.getMaximumLifetime()); // Close connections after 30 min
        config.setKeepaliveTime(settings.getKeepaliveTime()); // Reduce idle time (default is 60s)
        config.setConnectionTimeout(settings.getConnectionTimeout()); // Reduce timeout duration (default is 30000ms)
        return config;
    }

    @Blocking
    public Connection getConnection() {
        try {
            return source.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Blocking
    public void execute(String sql) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }catch (SQLException exp) {
            throw new RuntimeException(exp);
        }
    }

    @Blocking
    public void execute(String sql, Object... objects) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < objects.length; i++) {
                statement.setObject(i + 1, objects[i]);
            }
            statement.execute();
        } catch (SQLException exp) {
            throw new RuntimeException(exp);
        }
    }

    @Blocking
    public void update(String sql) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }catch (SQLException exp) {
            throw new RuntimeException(exp);
        }
    }

    @Blocking
    public void update(String sql, Object... objects) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < objects.length; i++) {
                statement.setObject(i + 1, objects[i]);
            }
            statement.executeUpdate();
        }catch (SQLException exp) {
            throw new RuntimeException(exp);
        }
    }

    @Blocking
    public void executeQuery(String sql, ResultExecutor executor) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)) {
            executor.process(result);
        } catch (SQLException exp) {
            throw new RuntimeException(exp);
        }
    }

    @Blocking
    public void executeQuery(String sql, ResultExecutor executor, Object... objects) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < objects.length; i++) {
                statement.setObject(i + 1, objects[i]);
            }
            try (ResultSet result = statement.executeQuery()) {
                executor.process(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Blocking
    public <T> T query(String sql, ResultQuery<T> query) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)) {
            return query.process(result);
        } catch (SQLException exp) {
            throw new RuntimeException(exp);
        }
    }

    @Blocking
    public <T> T query(String sql, ResultQuery<T> query, Object... objects) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < objects.length; i++) {
                statement.setObject(i + 1, objects[i]);
            }
            try (ResultSet result = statement.executeQuery()) {
                return query.process(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        if (source != null) {
            source.close();
        }
    }
}
