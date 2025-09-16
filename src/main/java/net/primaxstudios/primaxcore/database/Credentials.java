package net.primaxstudios.primaxcore.database;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Credentials {

    private final String host;
    private final int port;
    private final String databaseName;
    private final String username;
    private final String password;
    private String parameters;

    public Credentials(String host, int port, String databaseName, String username, String password, String parameters) {
        this.host = host;
        this.port = port;
        this.databaseName = databaseName;
        this.username = username;
        this.password = password;
        this.parameters = parameters;
    }
}
