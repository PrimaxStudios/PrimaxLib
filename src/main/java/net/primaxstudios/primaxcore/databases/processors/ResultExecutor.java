package net.primaxstudios.primaxcore.databases.processors;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultExecutor {

    void process(ResultSet result) throws SQLException;
}
