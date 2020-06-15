package com.breakpoint.controller;

import com.breakpoint.annotation.AccessLimit;
import com.breakpoint.configue.RedisCache;
import com.breakpoint.constans.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 缓存的刷新操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/04/09
 */
@Slf4j
@RestController
@RequestMapping("/v1/cache")
public class CacheRefreshController {

    /**
     * 留言的基本操作
     */
    @Resource
    private SimpleCacheManager simpleCacheManager;


    /**
     * 新增加 文章
     *
     * @return
     */
    @AccessLimit
    @PostMapping("/refresh")
    public Object refresh() {

        try {
            RedisCache topicCache = (RedisCache)simpleCacheManager.getCache("topicCache");
            topicCache.clear();
            return ResponseResult.createOK("OK");
        } catch (Exception e) {
            return ResponseResult.createFail(e.getMessage());
        }
    }


}
