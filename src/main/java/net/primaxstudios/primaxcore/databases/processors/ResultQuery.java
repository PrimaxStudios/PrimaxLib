package net.primaxstudios.primaxcore.databases.processors;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultQuery<T> {

    T process(ResultSet result) throws SQLException;
}

