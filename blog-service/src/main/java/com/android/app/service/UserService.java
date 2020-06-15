package com.android.app.service;

import com.android.app.entity.UserTable;
import com.breakpoint.exception.AppException;
import com.breakpoint.exception.BaseException;

/**
 * * 用户的基本操作
 * * 1，用户注册
 * * 2。修改密码
 * * 3 用户的注销
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/01/24
 */
public interface UserService {

    /**
     * 用户注册的基本操作
     *
     * @param userName
     * @param password
     * @param rePassword
     * @return
     * @throws AppException
     */
    Object register(String userName, String password, String rePassword, String nickName) throws BaseException;

    /**
     * 修改密码
     *
     * @param userTable  登陆用户
     * @param password   密码
     * @param rePassword 修改密码
     * @return
     * @throws AppException
     */
    Object reSetPassword(UserTable userTable, String oriPassword, String password, String rePassword) throws BaseException;

    /**
     * 用户注销
     *
     * @param userTable
     * @param password
     * @return
     * @throws AppException
     */
    Object userCancel(UserTable userTable, String password) throws BaseException;
}
