package com.breakpoint.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 用户的登录信息
 *
 * @author breakpoint/赵立刚
 * @date 2018/05/05
 */
public class UserInfo<T>{

    private static Map<String, Object> loginUserInfo = new ConcurrentHashMap<>(16);


    private static synchronized Map getLoginUserInfo() {
        if (null == loginUserInfo) {
            loginUserInfo = new ConcurrentHashMap<>(16);
        }
        return loginUserInfo;
    }


    /**
     * 获取到登录信息
     *
     * @param key
     * @return
     */
    public static synchronized <T> T getObject(String key, RedisTemplate redisTemplate) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        return (T)valueOperations.get(key);
    }

    /**
     * 移除
     *
     * @param key
     * @return
     */
    public static synchronized void removeObject(String key, RedisTemplate redisTemplate) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, null, 0, TimeUnit.MINUTES);
    }

    /**
     * 设置
     *
     * @param key
     * @param o
     * @return
     */
    public static synchronized Object setObject(String key, Object o, RedisTemplate redisTemplate) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, o, 30, TimeUnit.MINUTES);
        return "OK";
    }


}
