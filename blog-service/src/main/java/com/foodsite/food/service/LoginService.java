package com.foodsite.food.service;

import com.breakpoint.exception.BaseException;
import com.breakpoint.exception.FoodSiteException;

/**
 * @author :breakpoint/赵立刚
 * @date : 2019/01/12
 */
public interface LoginService {

    /**
     * 用户登陆
     *
     * @param username 用户名
     * @param password 密码
     * @return
     * @throws FoodSiteException
     */
    Object checkFoodUserLogin(String username, String password,String preShowKey) throws FoodSiteException,BaseException;

    /**
     * 注册
     *
     * @param username   用户名
     * @param password   密码
     * @param rePassword 确认密码
     * @return
     * @throws FoodSiteException
     */
    Object registerUser(String username, String password, String rePassword) throws FoodSiteException;
}
