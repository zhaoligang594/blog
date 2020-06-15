package com.breakpoint.controller;

import com.breakpoint.annotation.AccessLimit;
import com.breakpoint.constans.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author breakpoint/赵立刚
 * @date 2018/05/05
 */
@Slf4j
@RestController
@RequestMapping("/blog/v1/check")
public class CheckUserLoginController {

    @AccessLimit
    @GetMapping("/check")
    public Object check() {
        return ResponseResult.createOK("操作成功", "已经登录");
    }
}
