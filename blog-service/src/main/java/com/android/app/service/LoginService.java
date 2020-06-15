package com.android.app.service;

import com.breakpoint.exception.AppException;
import com.breakpoint.exception.BaseException;

/**
 * 用户的登陆
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/01/24
 */
public interface LoginService {

    /**
     * 基本的登陆操作
     * @param username
     * @param password
     * @return
     * @throws AppException
     */
    Object login(String username, String password) throws AppException,BaseException;
}
