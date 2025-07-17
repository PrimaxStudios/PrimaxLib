package net.primaxstudios.primaxcore.databases;

public interface DatabaseConnector {

    ConnectorType getConnectorType();
    DatabaseType getDatabaseType();
    void connect();
    void close();
}
