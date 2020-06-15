package com.breakpoint.service;

import com.breakpoint.exception.BlogException;

/**
 * @author :breakpoint/赵立刚
 * @date : 2019/05/11
 */
public interface BlogUserService {


    /**
     * 用户注册的操作
     *
     * @param nickname
     * @param userName
     * @return
     * @throws BlogException
     */
    Object registerUser(String nickname, String userName) throws BlogException;

    /**
     * 激活账户
     *
     * @param userNo   账户的编号
     * @param userName 账账户的用户名
     * @return
     * @throws BlogException
     */
    Object active(String userNo, String userName) throws BlogException;
}
