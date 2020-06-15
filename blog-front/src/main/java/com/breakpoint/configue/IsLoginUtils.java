package com.breakpoint.configue;

import com.breakpoint.util.TokenUtils;
import com.breakpoint.util.UserInfo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 判断用户是否进行了登陆的操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/04/02
 */
@Component
public class IsLoginUtils {

    /**
     * 基本的操作方式
     */
    @Resource
    private RedisTemplate redisTemplate;


    /**
     * 判断用户是否登陆了
     *
     * @param request
     * @return
     */
    public boolean isLogin(HttpServletRequest request) {

        String tokenKey = TokenUtils.getTokenFromHeaderOrRequestParamOrCookie(request);
        if (null == tokenKey) {
            return false;
        }


        Object object = UserInfo.getObject(tokenKey, redisTemplate);
        if (null == object) {
            return false;
        }

        return true;
    }

    public <T> T getLoginUser(HttpServletRequest request) {

        String tokenKey = TokenUtils.getTokenFromHeaderOrRequestParamOrCookie(request);
        if (null == tokenKey) {
            return null;
        }

        return (T)UserInfo.getObject(tokenKey, redisTemplate);

    }


}
