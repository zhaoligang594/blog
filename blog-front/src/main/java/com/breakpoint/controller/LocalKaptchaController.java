package com.breakpoint.controller;

import com.breakpoint.annotation.AccessLimit;
import com.breakpoint.constans.ResponseResult;
import com.kaptcha.LocalKaptchaService;
import com.redis.RedisSetObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 验证码的基本操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/01/14
 */
@Slf4j
@RestController
@RequestMapping("/blog/v1/image")
public class LocalKaptchaController {

    /**
     * 验证码的基本操作
     */
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 验证码
     */
    @Resource
    private LocalKaptchaService localKaptchaService;

    @AccessLimit(isLogIn = false,isEnableClickLimit = true)
    @GetMapping("/getKaptcha")
    public Object getKaptcha(){

        try{

            Map<String, String> returnMap = localKaptchaService.generateVerifyCode(4);


            ValueOperations valueOperations = redisTemplate.opsForValue();

            String key = UUID.randomUUID().toString();

            String key1 = RedisSetObject.getKey(key);
            /**
             * 存储1min
             */
            valueOperations.set(key1,returnMap.get("verifyCode"),1,TimeUnit.MINUTES);

            Map<String, String> map = new HashMap<>();
            map.put("verifyCodeKey",key);
            map.put("verifyCode",returnMap.get("verifyCodeString"));
            return ResponseResult.createOK(map);
        }catch (Exception e){
            return ResponseResult.createFail(e.getMessage());
        }
    }


}
