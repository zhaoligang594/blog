package com.breakpoint.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.breakpoint.exception.BlogException;
import com.breakpoint.service.AccessTokenService;
import com.breakpoint.util.pulicwx.WxUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 小程序的基本操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/10/26
 */
@Slf4j
@Service
public class AccessTokenServiceImpl implements AccessTokenService {

    @Resource
    private RedisTemplate redisTemplate;


    private static final String appId = "wx9ad7bcc3f0db6c7b";
    private static final String secret = "7a112a27f306b16cbc10732ddc1ce65a";
    private static final String TOKEN = "adgajgdagdhjgsajkbbjahdhasjdkhasdhsadhjg";

    /**
     * @return
     * @throws BlogException
     */
    @Override
    public Object getAccessTokenFromWxServer(String token) throws BlogException {

        if (StringUtils.equalsIgnoreCase(token, TOKEN)) {
            ValueOperations valueOperations = redisTemplate.opsForValue();

            String access_token_key = (String) valueOperations.get("access_token_key");

            if (StringUtils.isBlank(access_token_key)) {

                String message = WxUtils.doGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + secret);

                valueOperations.set("access_token_key", message, 7100, TimeUnit.SECONDS);
                access_token_key = message;
                log.info(message);

            }

            return JSONObject.parseObject(access_token_key);
        } else {
            throw new BlogException("token错误");
        }


    }

    /**
     * 获取到基本的信息
     *
     * @param token
     * @param code
     * @return
     * @throws BlogException
     */
    @Override
    public Object getSessionFromWxServer(String token, String code) throws BlogException {
        if (StringUtils.equalsIgnoreCase(token, TOKEN)) {
            String message = WxUtils.doGet("https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code");

            log.info(message);

            return JSONObject.parseObject(message);
        } else {
            throw new BlogException("token错误");
        }
    }
}
