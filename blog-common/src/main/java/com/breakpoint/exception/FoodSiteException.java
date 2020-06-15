package com.breakpoint.exception;

/**
 * 没事网站的
 * @author :breakpoint/赵立刚
 * @date : 2019/01/12
 */
public class FoodSiteException extends BaseException {

    public FoodSiteException(String message) {
        super(message);
    }

    public FoodSiteException(String message, Throwable cause) {
        super(message, cause);
    }

    public FoodSiteException(Throwable cause) {
        super(cause);
    }
}
