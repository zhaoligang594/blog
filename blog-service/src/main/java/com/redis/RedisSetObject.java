package com.redis;

import com.breakpoint.exception.BaseException;
import com.breakpoint.exception.FoodSiteException;
import com.foodsite.food.entity.FoodUser;
import org.apache.commons.lang.StringUtils;

/**
 * @author :breakpoint/赵立刚
 * @date : 2019/01/12
 */

public class RedisSetObject<T> extends BaseRedisSetObject{


    private  String LOGIN_PREFIX = "food_login_prefix";


    public RedisSetObject(String objectPrefix, T o) throws BaseException {
        super(objectPrefix,o);
    }

    /**
     * 获取登陆的redis 的类
     *
     * @param footUser
     * @param <T>
     * @return
     */
    public static <T> BaseRedisSetObject getLoginRedisSetObject(String key, FoodUser footUser) throws BaseException {
        return new RedisSetObject(getKey(key), footUser);
    }


    public static <T> BaseRedisSetObject getRedisSetObject(String key, Object object) throws BaseException {
        return new RedisSetObject(getKey(key), object);
    }


    public String getLOGIN_PREFIX() {
        return LOGIN_PREFIX;
    }

    @Override
    public String getLoginPrefix() {
        return LOGIN_PREFIX;
    }
}
