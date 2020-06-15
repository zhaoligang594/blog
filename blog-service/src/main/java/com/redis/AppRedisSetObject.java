package com.redis;

import com.breakpoint.exception.AppException;
import com.breakpoint.exception.BaseException;
import com.breakpoint.exception.FoodSiteException;
import com.foodsite.food.entity.FoodUser;
import org.apache.commons.lang.StringUtils;

/**
 * @author :breakpoint/赵立刚
 * @date : 2019/01/12
 */

public class AppRedisSetObject<T> extends BaseRedisSetObject{


    private static final String LOGIN_PREFIX = "app_login_prefix";


    public AppRedisSetObject(String objectPrefix, T o) throws BaseException {
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
        return new AppRedisSetObject(getKey(key), footUser);
    }


    public static <T> BaseRedisSetObject getRedisSetObject(String key, Object object) throws BaseException {
        return new AppRedisSetObject(getKey(key), object);
    }

    @Override
    public String getLoginPrefix() {
        return LOGIN_PREFIX;
    }
}
