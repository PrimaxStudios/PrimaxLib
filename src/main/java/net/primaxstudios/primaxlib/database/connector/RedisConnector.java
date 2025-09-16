package net.primaxstudios.primaxlib.database.connector;

import lombok.Getter;
import net.primaxstudios.primaxlib.database.mapper.JedisExecutor;
import net.primaxstudios.primaxlib.database.mapper.JedisQuery;
import org.jetbrains.annotations.Blocking;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Getter
public class RedisConnector {

    private final String host;
    private final int port;
    private final String password;
    private final boolean useSSL;
    private JedisPool jedisPool;

    public RedisConnector(String host, int port, String password, boolean useSSL) {
        this.host = host;
        this.port = port;
        this.password = password;
        this.useSSL = useSSL;
    }

    public void connect() {
        this.jedisPool = createPool();
    }

    public JedisPoolConfig getConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(50);
        poolConfig.setMaxIdle(10);
        poolConfig.setMinIdle(2);
        return poolConfig;
    }

    private JedisPool createPool() {
        if (password == null || password.isEmpty()) {
            return new JedisPool(getConfig(), host, port, 5000, useSSL);
        } else {
            return new JedisPool(getConfig(), host, port, 5000, password, useSSL);
        }
    }

    @Blocking
    public void execute(JedisExecutor executor) {
        try (Jedis jedis = jedisPool.getResource()) {
            executor.process(jedis);
        }
    }

    @Blocking
    public <T> T query(JedisQuery<T> query) {
        try (Jedis jedis = jedisPool.getResource()) {
            return query.process(jedis);
        }
    }

    public void close() {
        if (jedisPool != null) {
            jedisPool.close();
        }
    }
}
