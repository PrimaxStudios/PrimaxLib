package net.primaxstudios.primaxcore.database;

public interface DatabaseConnector {

    ConnectorType getConnectorType();
    DatabaseType getDatabaseType();
    void connect();
    void close();
}
