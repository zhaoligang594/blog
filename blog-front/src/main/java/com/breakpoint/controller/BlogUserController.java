package com.breakpoint.controller;

import com.breakpoint.annotation.AccessLimit;
import com.breakpoint.constans.ResponseResult;
import com.breakpoint.service.BlogUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 用户的基本操作
 * <p>
 * <p>
 * 正增加  用户  修改  用户 信息
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/05/11
 */
@Slf4j
@RestController
@RequestMapping("/blog/v1/user/")
public class BlogUserController {


    /**
     * 用户的基本操作
     */
    @Resource
    private BlogUserService blogUserService;


    /**
     * 用户注册
     * <p>
     * 用户的昵称 和用户的邮箱
     *
     * @return
     */
    @AccessLimit(isLogIn = false, isVerifyCode = true)
    @PostMapping("/register")
    public Object registerUser(@RequestParam("nickname") String nickName,
                               @RequestParam("userName") String userName) {


        try {
            Object o = blogUserService.registerUser(nickName, userName);

            return ResponseResult.createOK(o);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.createFail(e.getMessage());
        }
    }


    /**
     * 激活账户
     *
     * @param userNo
     * @param userName
     * @return
     */
    @AccessLimit(isLogIn = false, isVerifyCode = true)
    @GetMapping("/active/{userNo}/{userName}")
    public Object active(@PathVariable("userNo") String userNo,
                         @PathVariable("userName") String userName) {

        try {
            Object o = blogUserService.active(userNo, userName);

            return ResponseResult.createOK(o);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.createFail(e.getMessage());
        }
    }


}
