package com.breakpoint.controller;

import com.breakpoint.constans.ResponseResult;
import com.breakpoint.service.AccessTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 获取小程序的token的基本操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/10/26
 */
@Slf4j
@RestController
@RequestMapping("/blog/v1/access")
public class AccessTokenController {

    @Resource
    private AccessTokenService accessTokenService;


    @GetMapping("/getAccessTokenFromWxServer")
    public Object getAccessTokenFromWxServer(@RequestParam(value = "token", required = false) String token) {

        try {
            Object accessTokenFromWxServer = accessTokenService.getAccessTokenFromWxServer(token);
            return ResponseResult.createOK(accessTokenFromWxServer);
        } catch (Exception e) {
            return ResponseResult.createFail(e.getMessage());
        }
    }

    @GetMapping("/getSessionFromWxServer")
    public Object getSessionFromWxServer(@RequestParam(value = "token", required = false) String token, @RequestParam("code") String code) {

        try {
            Object accessTokenFromWxServer = accessTokenService.getSessionFromWxServer(token, code);
            return ResponseResult.createOK(accessTokenFromWxServer);
        } catch (Exception e) {
            return ResponseResult.createFail(e.getMessage());
        }
    }


}
