package com.kaptcha;

import java.io.IOException;
import java.util.Map;

/**
 * 验证码
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/01/14
 */
public interface LocalKaptchaService {

    /**
     * 生成验证码
     *
     * @param verifySize  验证码的长度
     * @return
     */
    Map<String,String> generateVerifyCode(int verifySize) throws IOException;
}
