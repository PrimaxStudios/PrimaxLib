package net.primaxstudios.primaxcore.database.mapper;

import redis.clients.jedis.Jedis;

@FunctionalInterface
public interface JedisExecutor {

    void process(Jedis jedis);
}
