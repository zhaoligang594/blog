package com.netty.server.configue;


import com.alibaba.fastjson.JSONObject;
import com.android.app.dao.UserTable2Mapper;
import com.android.app.entity.UserTable;
import com.breakpoint.annotation.AccessLimit;
import com.breakpoint.constans.InitConstants;
import com.breakpoint.constans.RetCodeConstant;
import com.breakpoint.util.ExploreWriteUtils;
import com.breakpoint.util.TokenUtils;
import com.redis.BaseRedisSetObject;
import com.redis.LocalRedisOpt;
import com.redis.RedisSetObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 拦截器
 * <p>
 * 验证验证码以及其他的操作
 * Created by Administrator on 2018/4/29 0029.
 */
@Slf4j
public class AccessLimitInterceptor extends HandlerInterceptorAdapter {

    /**
     * redis的基本操作
     */
    private RedisTemplate redisTemplate;


    /**
     * redis的基本操作
     */
    private LocalRedisOpt<UserTable> localRedisOpt;


    private UserTable2Mapper userTable2Mapper;


    public AccessLimitInterceptor(RedisTemplate redisTemplate, LocalRedisOpt<UserTable> localRedisOpt, UserTable2Mapper userTable2Mapper) {
        this.redisTemplate = redisTemplate;
        this.localRedisOpt = localRedisOpt;
        this.userTable2Mapper = userTable2Mapper;
    }

    /**
     * 限流防止频繁刷新
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {


        String verifyKey = request.getParameter("verifyKey");

        if (StringUtils.isEmpty(verifyKey)) {
            verifyKey = request.getHeader("verifyKey");
        }
        if (StringUtils.isEmpty(verifyKey)) {
            ExploreWriteUtils.writeMessage(RetCodeConstant.FAIL, request, response,
                    "无权访问");
            return false;
        } else if (!verifyKey.equals(InitConstants.APP_VERIFY_KEY)) {
            ExploreWriteUtils.writeMessage(RetCodeConstant.FAIL, request, response,
                    "无权访问");
            return false;
        }
        /**
         * 判断是否为方法
         */
        if (handler instanceof HandlerMethod) {
            /**
             * 向下强转
             */
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            AccessLimit methodAnnotation = handlerMethod.getMethodAnnotation(AccessLimit.class);
            if (null != methodAnnotation) {
                /**
                 * 具体的操作逻辑
                 */

                String remoteHost = request.getRemoteHost();

                String requestURI1 = request.getRequestURI();

                String intervalKey = RedisSetObject.getKey(remoteHost + requestURI1);

                ValueOperations valueOperations = redisTemplate.opsForValue();


                /**
                 * 操纵
                 */
                IntervalClass intervalClass = null;

                /**
                 * 能否继续向下进行执行
                 */
                boolean isCanpress = true;


                /**
                 * 取出来的时间
                 */
                String keyOptValue = (String) valueOperations.get(intervalKey);

                if (StringUtils.isEmpty(keyOptValue)) {
                    /**
                     * 第一次点击
                     */
                    intervalClass = new IntervalClass();
                    long currentTime = System.currentTimeMillis();
                    intervalClass.setFirstClickTime(currentTime);
                    intervalClass.setPreClickTime(currentTime);
                    intervalClass.setClickCount(1);
                } else {
                    /**
                     * 不是第一次点击
                     */
                    intervalClass = JSONObject.parseObject(keyOptValue, IntervalClass.class);

                    /**
                     * 获取当前时间
                     */
                    long currentTime = System.currentTimeMillis();

                    /**
                     * 点击的间隔的时间
                     */
                    long interval = methodAnnotation.interval();


                    if ((currentTime - intervalClass.getPreClickTime()) < interval) {
                        //todo 不符要求的请求
                        isCanpress = false;
                    } else {
                        //todo 符合要求的情形
                    }

                    /**
                     * 设置新值
                     */
                    intervalClass.setPreClickTime(currentTime);
                    intervalClass.setClickCount(intervalClass.getClickCount() + 1);


                }

                String secondString = JSONObject.toJSONString(intervalClass);

                /**
                 * 10秒自动消失
                 */
                valueOperations.set(intervalKey, secondString, 10, TimeUnit.SECONDS);

                /**
                 * 返回不能继续想象进行
                 */
                if (!isCanpress) {
                    ExploreWriteUtils.writeMessage(RetCodeConstant.TO_MANNY_REQUEST, request, response,
                            "您操作过于频繁，请间隔1s操作");
                    return false;
                }


                /**
                 * 验证验证码是否正确
                 */
                if (methodAnnotation.isVerifyCode()) {

                    /**
                     * 首先verifyCode存储在redis
                     *
                     */

                    String key = request.getParameter("verifyCodeKey");

                    if (StringUtils.isEmpty(key)) {
                        ExploreWriteUtils.writeMessage(RetCodeConstant.FAIL, request, response,
                                "请求参数中没有verifyCodeKey");
                        return false;
                    }

                    /**
                     * 获取到存储的key
                     */
                    String verifyCodeKey = RedisSetObject.getKey(key);

                    /**
                     * 验证码的基本操作 1min 时间
                     */
                    String verifyCode = (String) valueOperations.get(verifyCodeKey);


                    if (StringUtils.isEmpty(verifyCode)) {
                        ExploreWriteUtils.writeMessage(RetCodeConstant.ALERT, request, response,
                                "请刷新验证码");
                        return false;
                    }

                    /**
                     * 提交过来的验证码
                     */
                    String verifyCode1 = request.getParameter("verifyCode");


                    if (StringUtils.isEmpty(verifyCode1)) {
                        ExploreWriteUtils.writeMessage(RetCodeConstant.FAIL, request, response,
                                "请填写验证码");
                        return false;
                    }

                    /**
                     * 检验验证码是否一直
                     */

                    String verifyCodeRedis = verifyCode.toLowerCase().trim();
                    String verifyCodeReq = verifyCode1.toLowerCase().trim();
                    log.info(verifyCode);
                    log.info(verifyCodeRedis);
                    log.info(verifyCodeReq);

                    if (!verifyCodeRedis.equals(verifyCodeReq)) {
                        ExploreWriteUtils.writeMessage(RetCodeConstant.FAIL, request, response,
                                "验证码不正确");
                        return false;
                    }
                }


                /**
                 * 登录验证
                 */
                if (methodAnnotation.isLogIn()) {

                    /**
                     * 返回值
                     */
                    boolean returnValue = true;


                    /**
                     * 登陆的用户
                     */
                    UserTable userTable = null;

                    /**
                     * 获取登陆的token
                     */
                    String tokenKey = TokenUtils.getTokenFromHeaderOrRequestParamOrCookie(request);
                    if (null == tokenKey) {
                        returnValue = false;
                    } else {
                        String loginKey = RedisSetObject.getKey(tokenKey);

                        userTable = localRedisOpt.get(loginKey);
                        /**
                         * 检验是不是redis 失效了
                         */
                        if (null == userTable) {

                            userTable = userTable2Mapper.selectByLoginToken(loginKey);

                        }
                        if (null == userTable) {
                            returnValue = false;
                        } else {
                            /**
                             * 已经登陆了
                             */
                            BaseRedisSetObject redisSetObject = RedisSetObject.getRedisSetObject(loginKey, userTable);
                            localRedisOpt.set(redisSetObject);
                        }
                    }

                    if (!returnValue) {
                        /**
                         * 没有登陆的时候  记录登陆前的链接
                         */
                        String temp = "";

                        Map<String, String[]> parameterMap = request.getParameterMap();

                        Set<String> keySet = parameterMap.keySet();

                        for (String key : keySet) {
                            if (!"token".equals(key)) {
                                String[] strings = parameterMap.get(key);
                                temp += key + "=" + strings[0];
                            }
                            //for(String value:strings){
                            //    temp+=value+",";
                            //}
                        }
                        String requestURI = request.getRequestURI() + "?" + temp;
                        String requestURIToken = UUID.randomUUID().toString();
                        Cookie cookie = new Cookie("requestURIToken", requestURIToken);
                        System.out.println(requestURI);
                        response.addCookie(cookie);

                        if (!(requestURI.indexOf("islogin") >= 0)) {
                            localRedisOpt.set(RedisSetObject.getRedisSetObject(requestURIToken, requestURI));
                        }


                        ExploreWriteUtils.writeMessage(RetCodeConstant.未登录, request, response, "您没有登录，无法操作");
                    } else {
                        //TODO 登陆了的基本操作
                        /**
                         * 登陆了的基本操作
                         */


                        /**
                         * 1。登陆后  应当查看用户权限的是否可以操作
                         *
                         */


                        int methodPermission = methodAnnotation.permissionInt();

                        if (null != userTable) {
                            /**
                             * 如果方法的权限大于用户的权限，接口是不可用的
                             */
                            if (methodPermission > userTable.getPermission()) {

                                ExploreWriteUtils.writeMessage(RetCodeConstant.ALERT, request, response, "您没有权限，无法操作");
                                return false;

                            }
                        }

                    }

                    return returnValue;

                }


            }
        }
        return true;
    }

    /**
     * 错误的视图的返回
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        if (response.getStatus() == 500) {
//            modelAndView.setViewName("404");
//        } else if (response.getStatus() == 404) {
//            modelAndView.setViewName("404");
//        }
//        super.postHandle(request, response, handler, modelAndView);
//    }

}

/**
 * 序列化的对象
 */
@Data
class IntervalClass {

    /**
     * 上一次点击时间
     */
    private long preClickTime;
    /**
     * 第一次点击时间
     */
    private long firstClickTime;

    /**
     * 点击次数
     */
    private int clickCount;

}

