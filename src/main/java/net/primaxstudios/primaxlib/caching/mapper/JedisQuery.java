package net.primaxstudios.primaxlib.caching.mapper;

import redis.clients.jedis.Jedis;

@FunctionalInterface
public interface JedisQuery<T> {

    T process(Jedis jedis);
}
