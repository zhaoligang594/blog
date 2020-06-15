package com.breakpoint.service;

import com.breakpoint.exception.BlogException;

/**
 * @author :breakpoint/赵立刚
 * @date : 2019/10/26
 */
public interface AccessTokenService {

    /**
     * 获取小程序的token
     *
     * @return
     * @throws BlogException
     */
    Object getAccessTokenFromWxServer(String token) throws BlogException;

    Object getSessionFromWxServer(String token,String code) throws BlogException;
}
