package net.primaxstudios.primaxlib.caching.mapper;

import redis.clients.jedis.Jedis;

@FunctionalInterface
public interface JedisExecutor {

    void process(Jedis jedis);
}
