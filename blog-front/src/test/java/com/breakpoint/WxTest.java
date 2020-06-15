package com.breakpoint;

import com.breakpoint.exception.BlogException;
import com.breakpoint.util.pulicwx.WxUtils;

/**
 * @author :breakpoint/赵立刚
 * @date : 2020/04/01
 */
public class WxTest {
    public static void main(String[] args) throws BlogException {
        Object access_token = WxUtils.get_access_token();
    }
}
