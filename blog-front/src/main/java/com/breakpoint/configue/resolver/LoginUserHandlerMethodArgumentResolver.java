package com.breakpoint.configue.resolver;

import com.breakpoint.constans.RetCodeConstant;
import com.breakpoint.dto.LoginUserDto;
import com.breakpoint.util.ExploreWriteUtils;
import com.breakpoint.util.TokenUtils;
import com.breakpoint.util.UserInfo;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2018/4/29 0029.
 */
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {


    private RedisTemplate redisTemplate;


    public LoginUserHandlerMethodArgumentResolver(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 判断参数类型
     *
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(LoginUserDto.class);
    }

    /**
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        /**
         * j
         */
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);

        String tokenKey = TokenUtils.getTokenFromHeaderOrRequestParamOrCookie(request);
        if (null == tokenKey) {
            ExploreWriteUtils.writeMessage(RetCodeConstant.未登录, request, response, "您没有登录，无法操作");
            return null;
        }
        Object object = UserInfo.getObject(tokenKey,redisTemplate);
        if (null == object) {
            ExploreWriteUtils.writeMessage(RetCodeConstant.未登录, request, response, "您没有登录，无法操作");
            return null;
        }
        return object;
    }
}
