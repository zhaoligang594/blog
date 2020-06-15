package com.breakpoint.configue;

import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.breakpoint.configue.resolver.AccessMethodParamResolver;
import com.breakpoint.configue.resolver.LoginUserHandlerMethodArgumentResolver;
import com.breakpoint.configue.resolver.PageInfoResolver;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2018/4/5 0005.
 */
@Slf4j
@Configuration
public class WebMvcConfigure extends WebMvcConfigurerAdapter {


    @Resource
    private RedisTemplate redisTemplate;



    /**
     * <mvc:resources mapping="/js/**" location="/js/"/>
     * <mvc:resources mapping="/css/**" location="/css/"/>
     * <mvc:resources mapping="/image/**" location="/image/"/>
     * <mvc:resources mapping="/resume/**" location="/resume/"/>
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("classpath:/static/fonts/");
        registry.addResourceHandler("/image/**").addResourceLocations("classpath:/static/image/");
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
        registry.addResourceHandler("/resume/**").addResourceLocations("classpath:/static/resume/");
        registry.addResourceHandler("/ckeditor/**").addResourceLocations("classpath:/static/ckeditor/");
        registry.addResourceHandler("/layui/**").addResourceLocations("classpath:/static/layui/");
        registry.addResourceHandler("/editormd/**").addResourceLocations("classpath:/static/editormd/");
        registry.addResourceHandler("/echarts/**").addResourceLocations("classpath:/static/echarts/");
    }


    /**
     * 加入拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AccessLimitInterceptor(redisTemplate)).addPathPatterns("/**");
        registry.addInterceptor(deviceResolverHandlerInterceptor());
        super.addInterceptors(registry);
    }

    @Resource
    private AccessMethodParamResolver accessMethodParamResolver;

    /**
     * 加入参数的解析的
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(new LoginUserHandlerMethodArgumentResolver(redisTemplate));
        argumentResolvers.add(new PageInfoResolver());
        argumentResolvers.add(deviceHandlerMethodArgumentResolver());
        argumentResolvers.add(accessMethodParamResolver);
    }


    /**
     * 创建设备拦截器
     * @return
     */
    @Bean
    public DeviceResolverHandlerInterceptor
    deviceResolverHandlerInterceptor() {
        return new DeviceResolverHandlerInterceptor();
    }

    /**
     * 创建设备解析器
     * @return
     */
    @Bean
    public DeviceHandlerMethodArgumentResolver
    deviceHandlerMethodArgumentResolver() {
        return new DeviceHandlerMethodArgumentResolver();
    }


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);

//        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
//        ObjectMapper objectMapper = new ObjectMapper();
//        /**
//         * 序列换成json时,将所有的long变成string
//         * 因为js中得数字类型不能包含所有的java long值
//         */
//        SimpleModule simpleModule = new SimpleModule();
//
//        simpleModule.
//
//        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
//        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
//        objectMapper.registerModule(simpleModule);
//
//        jackson2HttpMessageConverter.setObjectMapper(objectMapper);

//        converters.add(jackson2HttpMessageConverter);

    }
}
