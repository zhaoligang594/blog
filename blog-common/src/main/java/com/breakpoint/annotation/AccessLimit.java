package com.breakpoint.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口防刷限流注解方式
 *
 * @author :breakpoint/赵立刚
 * @date : 2018/04/02
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {

    /**
     * 两次点击间隔时间  默认时间1000ms  单位ms
     *
     * @return
     */
    long interval() default 1000L;


    /**
     * 是否启用限流防刷的操作
     * @return
     */
    boolean isEnableClickLimit() default false;


    /**
     * 是否需要进行登录
     *
     * @return
     */
    boolean isLogIn() default true;
    /**
     * 冻结的账户是否可以进行通过   必须是 isLogIn 为true的情况下才有效
     *
     * @return
     */
    boolean freezeCanAccess() default false;
    /**
     * 该接口是否可用  也可以说接口是否可达
     *
     * @return
     */
    boolean enable() default true;
    /**
     * 验证码是否需要进行验证
     *
     * @return
     */
    boolean isVerifyCode() default false;
    /**
     * 权限值  大于等于才能进行操作
     * <p>
     * 当然 首先得闲登陆
     *
     * @return
     */
    int permissionInt() default 0;
}
