package com.breakpoint.util;


import com.breakpoint.exception.BlogException;
import org.apache.commons.lang3.StringUtils;

/**
 * 检验一些相关的数据
 */
public class LocalVerify {

    /**
     * 邮箱的正则
     */
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";

    /**
     * 密码的正则
     */
    private static final String PASSWOD_REGEX = "^[a-zA-Z0-9]{6,15}$";


    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean verifyEmail(String email) {

        if (null == email || "".equals(email)) {
            return false;
        } else if (email.matches(EMAIL_REGEX)) {
            return true;
        }

        return false;

    }

    /**
     * 验证密码
     *
     * @param password
     * @return
     */
    public static boolean verifyPassword(String password) {

        if (null == password || "".equals(password)) {
            return false;
        } else if (password.matches(PASSWOD_REGEX)) {
            return true;
        }

        return false;

    }



    /**
     * 检验是否大禹陵
     *
     * @param strs
     */
    public static void verifyStringIsNotNall(String... strs) throws BlogException {
        if (null != strs && strs.length > 0) {

            for (String str : strs) {
                if (StringUtils.isEmpty(str)) {
                    throw new BlogException(str + "=null;");
                }
            }

        }
    }
}
