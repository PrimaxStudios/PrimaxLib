package net.primaxstudios.primaxcore.database;

import lombok.Getter;

@Getter
public class PoolSettings {

    private final int maximumPoolSize;
    private final int minimumIdle;
    private final int maximumLifetime;
    private final int keepaliveTime;
    private final int connectionTimeout;

    public PoolSettings(int maximumPoolSize, int minimumIdle, int maximumLifetime, int keepaliveTime, int connectionTimeout) {
        this.maximumPoolSize = maximumPoolSize;
        this.minimumIdle = minimumIdle;
        this.maximumLifetime = maximumLifetime;
        this.keepaliveTime = keepaliveTime;
        this.connectionTimeout = connectionTimeout;
    }
}
