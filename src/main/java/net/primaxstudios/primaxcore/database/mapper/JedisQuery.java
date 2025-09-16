package net.primaxstudios.primaxcore.database.mapper;

import redis.clients.jedis.Jedis;

@FunctionalInterface
public interface JedisQuery<T> {

    T process(Jedis jedis);
}
