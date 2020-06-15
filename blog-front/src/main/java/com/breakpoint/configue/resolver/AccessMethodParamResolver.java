package com.breakpoint.configue.resolver;

import com.breakpoint.constans.RetCodeConstant;
import com.breakpoint.dto.LoginUserDto;
import com.breakpoint.util.ExploreWriteUtils;
import com.breakpoint.util.TokenUtils;
import com.breakpoint.util.UserInfo;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 参数的基本操作  一般用于commonController
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/05/13
 */
@Component
public class AccessMethodParamResolver implements HandlerMethodArgumentResolver {


    @Resource
    private RedisTemplate redisTemplate;


    /**
     * 判断是否为请求的参数
     *
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(AccessMethodParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {


        AccessMethodParam accessMethodParam = new AccessMethodParam();
        /**
         *  获取到本地的操作
         */
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);

        /**
         * 获取到基本参数
         */
        Integer role = (Integer) request.getAttribute("role");

        String tokenKey = TokenUtils.getTokenFromHeaderOrRequestParamOrCookie(request);
        if (null == tokenKey) {
            accessMethodParam.setCanProcess(false);
            return accessMethodParam;
        }

        LoginUserDto user = UserInfo.getObject(tokenKey, redisTemplate);
        if (null == user) {
            accessMethodParam.setCanProcess(false);
            return accessMethodParam;
        } else {


            if (role < user.getBlogUser().getUserRole()) {
                accessMethodParam.setCanProcess(true);
            } else {
                accessMethodParam.setCanProcess(false);
            }


            return accessMethodParam;
        }

    }
}
