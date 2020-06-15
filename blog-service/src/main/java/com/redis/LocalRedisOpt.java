package com.redis;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * redis 的基本操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/01/12
 */
@Component
public class LocalRedisOpt<T> {

    @Resource
    private RedisTemplate redisTemplate;
    /**
     * 前缀
     */
    private static final String REDIS_PREFIX = "food_prefix:";

    /**
     * 茶如织
     *
     * @param redisSetObject
     * @param timeUnit
     * @param time
     */
    public void set(BaseRedisSetObject redisSetObject, TimeUnit timeUnit, long time) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        /**
         * key
         */
        String key = REDIS_PREFIX + redisSetObject.getObjectPrefix();
        /**
         * 插入值
         */
        valueOperations.set(key, redisSetObject.getO(), time, timeUnit);
    }

    /**
     * @param redisSetObject 存储对象  默认30 分钟
     */
    public void set(BaseRedisSetObject redisSetObject) {
        this.set(redisSetObject, TimeUnit.MINUTES, 30);
    }

    public void expire(String keyPrefix) {
        try {
            String key = REDIS_PREFIX + keyPrefix;
            BaseRedisSetObject loginRedisSetObject = RedisSetObject.getLoginRedisSetObject(keyPrefix, null);
            this.set(loginRedisSetObject, TimeUnit.SECONDS, 1);
        } catch (Exception e) {

        }
    }


    public T get(String keyPrefix) {
        String key = REDIS_PREFIX + keyPrefix;
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object o = valueOperations.get(key);
        return (T) o;
    }

}
