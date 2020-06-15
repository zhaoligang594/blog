package com.breakpoint.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.breakpoint.exception.BlogException;
import com.breakpoint.service.WxServerService;
import com.breakpoint.util.GenerateIDUtils;
import com.breakpoint.util.pulicwx.WxUtils;
import com.breakpoint.util.wx.AesException;
import com.breakpoint.util.wx.SHA1;
import com.breakpoint.util.wx.SignUtil;
import com.breakpoint.util.wx.XMLParse;
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
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author :breakpoint/赵立刚
 * @date : 2020/03/30
 */
@Slf4j
@Service
public class WxServerServiceImpl implements WxServerService {

    @Resource
    private RedisTemplate redisTemplate;

    private static final String ACCESS_TOKEN_KEY = "access_token_key_public";
    private static final String JS_TOKEN_KEY = "js_token_key_public";

    /**
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     * @throws BlogException
     */
    @Override
    public Object check(String signature, String timestamp, String nonce, String echostr) throws Exception {

        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            return echostr;

        } else {
            return null;
        }

    }

    @Override
    public Object wxService(String signature, String timestamp, String nonce, String openid, String encrypt_type,
                            String msg_signature, HttpServletRequest request) throws Exception {

        String messageFromWX = getMessageFromWX(signature, timestamp, nonce, openid, encrypt_type, msg_signature, request);

        log.info("messageFromWX={}", messageFromWX);
        String replyMsg = "<xml>"
                + "<ToUserName><![CDATA[" + openid + "]]></ToUserName>"//回复用户时，这里是用户的openid；但用户发送过来消息这里是微信公众号的原始id
                + "<FromUserName><![CDATA[" + "gh_8742820aee98" + "]]></FromUserName>"//这里填写微信公众号 的原始id；用户发送过来时这里是用户的openid
                + "<CreateTime>1531553112194</CreateTime>"//这里可以填创建信息的时间，目前测试随便填也可以
                + "<MsgType><![CDATA[text]]></MsgType>"//文本类型，text，可以不改
                + "<Content><![CDATA[我喜欢你]]></Content>"//文本内容，我喜欢你
                + "<MsgId>1234567890123456</MsgId> "//消息id，随便填，但位数要够
                + " </xml>";

        //String s = SignUtil.ecryptMsg(replyMsg, timestamp, nonce);

        return replyMsg;
    }

    @Override
    public JSONObject getAccessTokenForPublic() throws BlogException {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        JSONObject o = (JSONObject) valueOperations.get(ACCESS_TOKEN_KEY);
        if (null == o) {
            o = (JSONObject) WxUtils.get_access_token();
            valueOperations.set(ACCESS_TOKEN_KEY, o, (7200 - 5 * 60), TimeUnit.SECONDS);
        }
        return o;
    }

    @Override
    public JSONObject getJsapiTicketForPublic(String accessToken) throws BlogException {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        JSONObject o = (JSONObject) valueOperations.get(JS_TOKEN_KEY + "-" + accessToken);
        if (null == o) {
            o = (JSONObject) WxUtils.get_jsapi_ticket(accessToken);
            valueOperations.set(JS_TOKEN_KEY + "-" + accessToken, o, (7200 - 5 * 60), TimeUnit.SECONDS);
        }
        return o;
    }


    @Override
    public Object signatureForPublic(String url) throws Exception {

        if (StringUtils.isBlank(url)) {
            throw new BlogException("请求的链接是空的");

        } else {
            Map<String, Object> map = new HashMap<>(5);
            String noncestr = GenerateIDUtils.getUniqueID32Length();
            String timestamp = System.currentTimeMillis() + "";
            JSONObject object = getAccessTokenForPublic();
            JSONObject access_token = getJsapiTicketForPublic(object.getString("access_token"));
            String jsapi_ticket = access_token.getString("ticket");
            map.put("noncestr", noncestr);
            map.put("timestamp", timestamp);
            map.put("url", url);
            map.put("appId", WxUtils.appId);
            String signature = SHA1.getSHA1ForPublic("url=" + url, "timestamp=" + timestamp,
                    "noncestr=" + noncestr, "jsapi_ticket=" + jsapi_ticket);
            map.put("signature", signature);
            return map;

        }
    }


    private String getMessageFromWX(String signature, String timestamp, String nonce, String openid, String encrypt_type,
                                    String msg_signature, HttpServletRequest request) throws Exception {


        if (encrypt_type == null || encrypt_type.equals("raw")) {////不用加密
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                //业务处理方法，返回为XML格式的结果信息 此处需要转换一下编码,否则中文乱码，不知为何...
                //String respMessage = new String(getRequestStr(request)).getBytes("UTF-8"), "ISO8859_1");
                String respMessage = getRequestStr(request);
                log.info("message:" + respMessage);
                return respMessage;
            } else {
                return "check Error";
            }
        } else {

            String encryptMsgXml = getRequestStr(request);

            log.info("encryptMsg={}", encryptMsgXml);
            return encryptMsgXml;

        }


    }

    private String getRequestStr(HttpServletRequest request) {

        BufferedReader fileReader = null;

        try {

            ServletInputStream inputStream = request.getInputStream();
            fileReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();
            String line = null;
            while ((null != (line = fileReader.readLine()))) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {

            if (null != fileReader) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }


}
