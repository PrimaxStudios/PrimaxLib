package net.primaxstudios.primaxcore.caches.processors;

import redis.clients.jedis.Jedis;

@FunctionalInterface
public interface JedisQuery<T> {

    T process(Jedis jedis);
}
