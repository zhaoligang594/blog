package com.breakpoint.service;

import com.breakpoint.exception.BlogException;

/**
 * @author breakpoint/赵立刚
 * @date 2018/05/05
 */
public interface LoginService {

    /**
     * 用户进行登录
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    String checkUserLogin(String username, String password) throws BlogException;
}
