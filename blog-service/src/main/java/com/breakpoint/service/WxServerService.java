package com.breakpoint.service;

import com.breakpoint.exception.BlogException;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信的基本操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2020/03/30
 */
public interface WxServerService {

    String WX_TOKEN = "jshdjhsjhdjshjdhsakjash";

    /**
     * 检查我们的服务
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     * @throws BlogException
     */
    Object check(String signature, String timestamp, String nonce, String echostr) throws Exception;

    Object wxService(String signature, String timestamp, String nonce, String openid, String encrypt_type,
                     String msg_signature, HttpServletRequest request) throws Exception;

    /**
     * 微信的基本操作
     */

    /**
     * @return
     * @throws BlogException
     */
    Object getAccessTokenForPublic() throws BlogException;

    Object getJsapiTicketForPublic(String accessToken) throws BlogException;

    /**
     * 签名我们的基本操作
     *
     * @param url
     * @return
     * @throws BlogException
     */
    Object signatureForPublic(String url) throws Exception;
}
