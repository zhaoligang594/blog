package com.breakpoint.util.pulicwx;

import com.alibaba.fastjson.JSONObject;
import com.breakpoint.exception.BlogException;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author :breakpoint/赵立刚
 * @date : 2020/04/01
 */
public class WxUtils {

    public static final String appId = "wx3eab8a4abb0da1d6";
    private static final String appSecret = "f7df83579862a632ef8b74e1cfc30a06";

    public static Object get_access_token() throws BlogException {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret;
        return JSONObject.parseObject(doGet(url));
    }

    public static Object get_jsapi_ticket(String accessToken) throws BlogException {
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi";
        return JSONObject.parseObject(doGet(url));
    }

    /**
     * get 的基本请求
     *
     * @param url
     * @return
     * @throws BlogException
     */
    public static String doGet(String url) throws BlogException {
        String returnStr = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                returnStr = EntityUtils.toString(responseEntity);
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                System.out.println("响应内容为:" + returnStr);
            }
        } catch (ClientProtocolException e) {
            throw new BlogException(e.getMessage());
        } catch (ParseException e) {
            throw new BlogException(e.getMessage());
        } catch (IOException e) {
            throw new BlogException(e.getMessage());
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                throw new BlogException(e.getMessage());
            }
        }

        return returnStr;

    }
}
