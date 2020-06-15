package com.breakpoint.configue;


import com.alibaba.fastjson.JSONObject;
import com.breakpoint.annotation.AccessLimit;
import com.breakpoint.constans.RetCodeConstant;
import com.breakpoint.dto.LoginUserDto;
import com.breakpoint.entity.BlogUser;
import com.breakpoint.util.ExploreWriteUtils;
import com.breakpoint.util.RedisUtils;
import com.breakpoint.util.TokenUtils;
import com.breakpoint.util.UserInfo;
import com.redis.RedisSetObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * 检验用户登陆以及其他操作  增加接口是否可用的操作
 * Created by Administrator on 2018/4/29 0029.
 */
@Slf4j
public class AccessLimitInterceptor extends HandlerInterceptorAdapter {

    private RedisTemplate redisTemplate;


    public AccessLimitInterceptor(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
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

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Access-Token,token,is");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Access-Control-Expose-Headers", "*");
        response.setHeader("Access-Control-Request-Headers", "Origin, X-Requested-With, content-Type, Accept, Authorization");
        //response.setHeader("", "GET, POST");
        response.setCharacterEncoding("UTF-8");



        if (handler instanceof HandlerMethod) {
            /**
             * 向下强转
             */
            HandlerMethod handlerMethod = (HandlerMethod) handler;


            AccessLimit methodAnnotation = handlerMethod.getMethodAnnotation(AccessLimit.class);


            if (null != methodAnnotation) {

                /**
                 *
                 */
                request.setAttribute("role", methodAnnotation.permissionInt());
                /**
                 * 具体的操作逻辑
                 */

                ValueOperations valueOperations = redisTemplate.opsForValue();




                if(methodAnnotation.isEnableClickLimit()){

                    //只有启用后，才能操作

                    /**
                     * 限流防刷
                     */

                    /*    限流防刷操作  start   */

                    String remoteHost = request.getRemoteHost();

                    String requestURI1 = request.getRequestURI();

                    String intervalKey = RedisUtils.getRedisKey(remoteHost + requestURI1);

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





                }





                /*    限流防刷操作  end   */


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

                    //System.out.println(verifyCode);

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
                 * 验证验证码是否正确  end
                 */


                /**
                 * 登录验证
                 */
                if (methodAnnotation.isLogIn()) {

                    String tokenKey = TokenUtils.getTokenFromHeaderOrRequestParamOrCookie(request);
                    if (null == tokenKey) {
                        ExploreWriteUtils.writeMessage(RetCodeConstant.未登录, request, response, "您没有登录，无法操作");
                        return false;
                    }

                    LoginUserDto user = UserInfo.getObject(tokenKey, redisTemplate);
                    if (null == user) {
                        ExploreWriteUtils.writeMessage(RetCodeConstant.未登录, request, response, "您没有登录，无法操作");
                        return false;
                    }


                    /**
                     * 权限验证
                     */

                    int role = methodAnnotation.permissionInt();

                    /**
                     * 访问的接口大于本身可以请求的  请求失败
                     */
                    if (role > user.getBlogUser().getUserRole()) {
                        ExploreWriteUtils.writeMessage(RetCodeConstant.ALERT, request, response, "您没有权限访问该接口");
                        return false;
                    }


                    request.setAttribute("role", role);

                }

            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (response.getStatus() == 500) {
                modelAndView.setViewName("404");
        } else if (response.getStatus() == 404) {
            if (null != modelAndView) {
                modelAndView.setViewName("404");
            }
        }
        super.postHandle(request, response, handler, modelAndView);
    }
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
