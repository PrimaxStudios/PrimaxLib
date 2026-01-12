package net.primaxstudios.primaxlib.database;

public interface DatabaseConnector {

    ConnectorType getConnectorType();
    void connect();
    void close();
}
