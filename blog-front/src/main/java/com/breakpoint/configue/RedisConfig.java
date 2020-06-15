package com.breakpoint.configue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Administrator on 2018/4/29 0029.
 */
@Configuration
public class RedisConfig {


    @Value("${redis.maxTotal}")
    int maxTotal;
    @Value("${redis.maxIdle}")
    int maxIdle;
    @Value("${redis.maxWaitMillis}")
    int maxWaitMillis;
    @Value("${redis.minIdle}")
    int minIdle;
    @Value("${redis.hostName}")
    String hostName;
    @Value("${redis.password}")
    String password;
    @Value("${redis.port}")
    int port;
    @Value("${redis.timeout}")
    int timeout;


    @Primary
    @Bean
    public JedisPoolConfig getJedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        return jedisPoolConfig;
    }


    @Primary
    @Bean
    public JedisConnectionFactory getJedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
        jedisConnectionFactory.setHostName(hostName);
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setTimeout(timeout);
        return jedisConnectionFactory;
    }


    @Primary
    @Bean
    public RedisTemplate getRedisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(new GenericToStringSerializer(String.class));
        redisTemplate.setHashKeySerializer(new GenericToStringSerializer(String.class));
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    @Bean("simpleCacheManager")
    public SimpleCacheManager getSimpleCacheManager(RedisTemplate redisTemplate) {

        Collection<Cache> caches = new ArrayList<>();
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();

        /**
         * 优惠卷的基本操作
         */
        RedisCache cache=new RedisCache();
        cache.setRedisTemplate(redisTemplate);
        cache.setName("yuhuijuan");
        caches.add(cache);

        RedisCache cacheTopic=new RedisCache();
        cacheTopic.setRedisTemplate(redisTemplate);
        cacheTopic.setName("topicCache");
        caches.add(cacheTopic);

        RedisCache bbsCache=new RedisCache();
        bbsCache.setRedisTemplate(redisTemplate);
        bbsCache.setName("bbsCache");
        caches.add(bbsCache);

        simpleCacheManager.setCaches(caches);
        return simpleCacheManager;
    }


}
