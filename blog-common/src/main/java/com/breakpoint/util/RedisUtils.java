package com.breakpoint.util;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * redis 的基本操作
 */
public class RedisUtils {


    /*  redis 的存储前缀  */
    private static final String PROJECT_REDIS_PREFIX = "blog_test";

    /*  默认的时间单位  */
    private static TimeUnit defaultTimeUnit = TimeUnit.MINUTES;

    /*   默认的缓存时长  */
    private static long defaultTimeOut = 30L;


    /**
     * 获取到存储的key
     *
     * @param oriKey
     * @return
     */
    public static String getRedisKey(String oriKey) {

        return PROJECT_REDIS_PREFIX + ":" + oriKey;
    }

    /**
     * 获取到值
     *
     * @param oriKey        原来的key
     * @param redisTemplate
     * @param <T>
     * @return
     */
    public static <T> T getObjectByOriKey(String oriKey, RedisTemplate redisTemplate) {

        if (null == oriKey) {
            return null;
        }

        String key = getRedisKey(oriKey);
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object o = valueOperations.get(key);
        return (T) o;
    }

    /**
     * @param oriKey
     * @param redisTemplate
     * @param object
     */
    public static void setObjectByOriKey(String oriKey, RedisTemplate redisTemplate, Object object) {

        setObjectByOriKey(oriKey, redisTemplate, object, defaultTimeOut, defaultTimeUnit);
    }

    public static void setObjectByOriKey(String oriKey, RedisTemplate redisTemplate, Object object, Long timeOut, TimeUnit timeUnit) {


        String key = getRedisKey(oriKey);

        ValueOperations valueOperations = redisTemplate.opsForValue();

        if (null == timeOut) {
            timeOut = defaultTimeOut;
        }

        if (null == timeUnit) {
            timeUnit = defaultTimeUnit;
        }

        valueOperations.set(key, object, timeOut, timeUnit);
    }


}

