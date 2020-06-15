package com.netty.server.user;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

/**
 * 登陆用户的上下文
 * <p>
 * 用户登陆后可以进行存储用户的登陆的上下文以方便用户的相互交流
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/01/31
 */
public class UsersContext {

    /**
     * 定义所有登陆的用户  key用户登陆的key
     * <p>
     * value用户登陆的 上下文
     */
    private static Map<String, ChannelHandlerContext> allLoginUser = new HashMap<>(16);


    /**
     * 获取到登陆的用户
     *
     * @param userKey
     * @return
     */
    public static ChannelHandlerContext getLoginUserContext(String userKey) {
        if (null != allLoginUser) {

            return allLoginUser.get(userKey);
        }
        return null;
    }

    /**
     * 登陆一个用户
     *
     * @param userKey
     * @param ctx
     */
    public static void addLoginUserContext(String userKey, ChannelHandlerContext ctx) {
        allLoginUser.put(userKey, ctx);
    }

    /**
     * 用户的下线
     *
     * @param userKey
     */
    public static void removeLoginUserContext(String userKey) {
        allLoginUser.remove(userKey);
    }


}
