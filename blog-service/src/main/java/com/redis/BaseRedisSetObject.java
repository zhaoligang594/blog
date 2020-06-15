package com.redis;

import com.breakpoint.exception.BaseException;
import com.breakpoint.exception.FoodSiteException;
import com.foodsite.food.entity.FoodUser;
import org.apache.commons.lang.StringUtils;

/**
 * @author :breakpoint/赵立刚
 * @date : 2019/01/12
 */

public abstract class BaseRedisSetObject<T> {


    /**
     * 操作前缀
     */
    protected String objectPrefix;

    /**
     * 对象
     */
    protected T o;


    private static final String LOGIN_PREFIX = "login_prefix";


    public String getObjectPrefix() {
        return objectPrefix;
    }

    public void setObjectPrefix(String objectPrefix) {
        this.objectPrefix = objectPrefix;
    }

    public T getO() {
        return o;
    }

    public void setO(T o) {
        this.o = o;
    }

    public BaseRedisSetObject(String objectPrefix, T o) throws BaseException {
        if (StringUtils.isEmpty(objectPrefix)) {
            throw new FoodSiteException("objectPrefix==null");
        }
        this.objectPrefix = objectPrefix;
        this.o = o;
    }





    public static <T> String getKey(String key) throws BaseException {
        return  LOGIN_PREFIX + ":" + key;
    }

    public abstract String getLoginPrefix();
}
