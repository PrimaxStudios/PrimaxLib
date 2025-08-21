package net.primaxstudios.primaxcore.databases.connectors;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.primaxstudios.primaxcore.databases.DatabaseType;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import javax.sql.DataSource;

public class MysqlConnector2 extends SqlConnector {

    private static DSLContext ctx;
    private static HikariDataSource ds;

    public static void init() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/mcdb");
        config.setUsername("postgres");
        config.setPassword("password");
        config.setMaximumPoolSize(10); // adjust as needed

        ds = new HikariDataSource(config);
        ctx = DSL.using(ds, org.jooq.SQLDialect.POSTGRES);
    }

    public static DSLContext getCtx() {
        return ctx;
    }

    public static DataSource getDataSource() {
        return ds;
    }

    public static void close() {
        if (ds != null) ds.close();
    }

    @Override
    public DatabaseType getDatabaseType() {
        return DatabaseType.MYSQL;
    }
}
