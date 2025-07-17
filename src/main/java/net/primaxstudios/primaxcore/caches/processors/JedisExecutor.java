package net.primaxstudios.primaxcore.caches.processors;

import redis.clients.jedis.Jedis;

@FunctionalInterface
public interface JedisExecutor {

    void process(Jedis jedis);
}
