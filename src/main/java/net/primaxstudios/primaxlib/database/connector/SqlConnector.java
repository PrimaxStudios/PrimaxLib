package net.primaxstudios.primaxlib.database.connector;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;
import net.primaxstudios.primaxlib.database.DatabaseConnector;
import net.primaxstudios.primaxlib.database.PoolSettings;
import net.primaxstudios.primaxlib.database.mapper.ResultExecutor;
import net.primaxstudios.primaxlib.database.mapper.ResultQuery;
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
    public boolean execute(String sql, Object... params) {
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            bindParams(stmt, params);
            return stmt.execute();
        } catch (SQLException exp) {
            throw new RuntimeException(exp);
        }
    }

    @Blocking
    public int[] executeBatch(String sql, Object[][] batchParams) {
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (Object[] params : batchParams) {
                bindParams(stmt, params);
                stmt.addBatch();
            }
            return stmt.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Blocking
    public int insert(String sql, Object... params) {
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            bindParams(stmt, params);
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1); // return the first generated key
                }
            }
            return -1; // no key generated
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Blocking
    public <T> T insert(String sql, ResultQuery<T> keyHandler, Object... params) {
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            bindParams(stmt, params);
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                return keyHandler.process(keys); // caller decides what to do
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Blocking
    public int update(String sql, Object... params) {
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            bindParams(stmt, params);
            return stmt.executeUpdate();
        } catch (SQLException exp) {
            throw new RuntimeException(exp);
        }
    }

    @Blocking
    public void executeQuery(String sql, ResultExecutor executor, Object... params) {
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            bindParams(stmt, params);
            try (ResultSet result = stmt.executeQuery()) {
                executor.process(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Blocking
    public <T> T query(String sql, ResultQuery<T> query, Object... params) {
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            bindParams(stmt, params);
            try (ResultSet result = stmt.executeQuery()) {
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

    private void bindParams(PreparedStatement stmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
    }
}
