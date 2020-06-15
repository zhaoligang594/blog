package com.android.app.controller;

import com.android.app.entity.UserTable;
import com.android.app.service.UserService;
import com.breakpoint.annotation.AccessLimit;
import com.breakpoint.constans.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户的基本操作
 * 1，用户注册
 * 2。修改密码
 * 3 用户的注销
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/01/24
 */
@Slf4j
@RestController
@RequestMapping("/app/v1/user")
public class UserController {


    @Resource
    private UserService userService;

    /**
     * 用户注册
     *
     * @return
     */
    @AccessLimit(isLogIn = false)
    @PostMapping("/register/{userName}/{password}/{rePassword}")
    public Object register(@PathVariable("userName") String userName,
                           @PathVariable("password") String password,
                           @PathVariable("rePassword") String rePassword,
                           @RequestParam("nickName") String nickName) {

        try {
            Object register = userService.register(userName, password, rePassword,nickName);
            return ResponseResult.createOK(register);
        } catch (Exception e) {
            return ResponseResult.createFail(e.getMessage());
        }
    }


    /**
     * 修改密码
     *
     * @param userTable   登陆的用户
     * @param password    密码
     * @param oriPassword 愿密码
     * @param rePassword  确认密码
     * @return
     */
    @AccessLimit
    @PostMapping("/reSetPassword/{oriPassword}/{password}/{rePassword}")
    public Object reSetPassword(UserTable userTable, @PathVariable("password") String password,
                                @PathVariable("oriPassword") String oriPassword,
                                @PathVariable("rePassword") String rePassword) {
        try {
            if (null == userTable) {
                return null;
            }
            /**
             * 修改密码的基本操作
             */
            Object register = userService.reSetPassword(userTable, oriPassword, password, rePassword);
            return ResponseResult.createOK(register);
        } catch (Exception e) {
            return ResponseResult.createFail(e.getMessage());
        }
    }

    /**
     * 注销用户
     *
     * @param userTable 登陆用户
     * @param password  密码
     * @return
     */
    @AccessLimit(isVerifyCode = true)
    @PostMapping("/userCancel/{password}")
    public Object userCancel(UserTable userTable, @PathVariable("password") String password) {
        try {

            if (null == userTable) {
                return null;
            }
            /**
             * 修改密码的基本操作
             */
            Object register = userService.userCancel(userTable, password);
            return ResponseResult.createOK(register);
        } catch (Exception e) {
            return ResponseResult.createFail(e.getMessage());
        }
    }


}
