package net.primaxstudios.primaxcore.database.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultExecutor {

    void process(ResultSet result) throws SQLException;
}
