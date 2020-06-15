package com.android.app.controller;

import com.android.app.service.LoginService;
import com.breakpoint.annotation.AccessLimit;
import com.breakpoint.constans.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户登陆的基本操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/01/24
 */
@Slf4j
@RestController
@RequestMapping("/app/v1/login")
public class LoginController {


    /**
     * 基本的登陆
     */
    @Resource
    private LoginService loginService;


    @AccessLimit(isVerifyCode = false,isLogIn = false)
    @PostMapping("/login/{username}/{password}")
    public Object login(@PathVariable("username") String username,
                        @PathVariable("password") String password) {
        try {
            /**
             * 修改密码的基本操作
             */
            Object register = loginService.login(username, password);
            return ResponseResult.createOK(register);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.createFail(e.getMessage());
        }
    }
}
