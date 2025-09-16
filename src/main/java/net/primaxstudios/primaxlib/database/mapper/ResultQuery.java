package net.primaxstudios.primaxlib.database.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultQuery<T> {

    T process(ResultSet result) throws SQLException;
}

