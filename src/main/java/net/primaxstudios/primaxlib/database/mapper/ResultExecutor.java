package net.primaxstudios.primaxlib.database.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultExecutor {

    void process(ResultSet result) throws SQLException;
}
