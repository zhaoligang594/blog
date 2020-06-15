package com.breakpoint.exception;

/**
 * 没事网站的
 * @author :breakpoint/赵立刚
 * @date : 2019/01/12
 */
public class AppException extends BaseException {

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(Throwable cause) {
        super(cause);
    }
}
