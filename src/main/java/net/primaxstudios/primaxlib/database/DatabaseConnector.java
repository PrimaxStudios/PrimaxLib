package net.primaxstudios.primaxlib.database;

public interface DatabaseConnector {

    DatabaseType getDatabaseType();
    void connect();
    void close();
}
