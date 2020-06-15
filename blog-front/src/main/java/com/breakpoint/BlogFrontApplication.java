package com.breakpoint;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 支持缓存以事物处理
 */
@MapperScan("com.breakpoint.dao")
@ComponentScan({"com.email","com.ftp","com.kaptcha","com.breakpoint.service", "com.breakpoint.controller","com.breakpoint.configue"})
@EnableCaching
@EnableConfigurationProperties
@EnableTransactionManagement
@SpringBootApplication
public class BlogFrontApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BlogFrontApplication.class, args);
    }


    /**
     * 支持tomcat部署
     *
     * @param builder
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(BlogFrontApplication.class);
    }
}
