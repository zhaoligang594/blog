package com.breakpoint.controller;


import com.breakpoint.annotation.AccessLimit;
import com.breakpoint.constans.ResponseResult;
import com.breakpoint.exception.BlogException;
import com.breakpoint.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author breakpoint/赵立刚
 * @date 2018/05/05
 */
@Slf4j
@RestController
@RequestMapping("/blog/v1/login")
public class LoginController {

    @Resource
    private LoginService loginService;


    @AccessLimit(isLogIn = false,isVerifyCode = false)
    @PostMapping("/login/{username}/{password}")
    public Object login(@PathVariable("username") String username, @PathVariable("password") String password,
                        HttpServletRequest request, HttpServletResponse response) {

        try {
            String o = loginService.checkUserLogin(username, password);

            Cookie cookie=new Cookie("token",o);
            cookie.setMaxAge(60*60);
            cookie.setPath("/");
            response.addCookie(cookie);

            return ResponseResult.createOK(o);
        } catch (BlogException e) {
            //e.printStackTrace();
            return ResponseResult.createFailResult("操作失败", e.getMessage());
        }

    }



}
