package net.primaxstudios.primaxlib.database;

public interface DatabaseConnector {

    ConnectorType getConnectorType();
    DatabaseType getDatabaseType();
    void connect();
    void close();
}
