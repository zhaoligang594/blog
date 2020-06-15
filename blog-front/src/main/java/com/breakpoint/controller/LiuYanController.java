package com.breakpoint.controller;

import com.breakpoint.annotation.AccessLimit;
import com.breakpoint.constans.ResponseResult;
import com.breakpoint.dto.LoginUserDto;
import com.breakpoint.dto.PageInfo;
import com.breakpoint.exception.BlogException;
import com.breakpoint.service.LiuYanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 留言接口的具体操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/04/09
 */
@Slf4j
@RestController
@RequestMapping("/v1/liuyan")
public class LiuYanController {

    /**
     * 留言的基本操作
     */
    @Resource
    private LiuYanService liuYanService;

    /**
     * 新增加 文章
     *
     * @return
     */
    @AccessLimit(isLogIn = false, isVerifyCode = true)
    @PostMapping("/save")
    public Object saveTopic(@RequestParam("message") String message,
                            @RequestParam("userName") String userName,
                            @RequestParam(value = "pictureUrl", required = false) String pictureUrl) {

        try {
            Object insert = liuYanService.insert(userName, message, pictureUrl);
            return ResponseResult.createOK(insert);
        } catch (Exception e) {
            return ResponseResult.createFailResult("操作失败", e.getMessage());
        }
    }

    /**
     * 以前的版本
     *
     * @param pageInfo
     * @return
     */
    @GetMapping("/getYouLiuYanByPage")
    public Object getTopicByPageInfo(PageInfo pageInfo) {
        try {
            pageInfo = liuYanService.getYouLiuYanByPageInfoForSite(pageInfo, null);
        } catch (BlogException e) {
            //e.printStackTrace();
            //return ResponseResult.createFail(e.getMessage());
        }

        return pageInfo;
    }

    /**
     * 获取到留言的基本信息
     *
     * @param pageInfo
     * @return
     */
    @AccessLimit(isLogIn = false)
    @GetMapping("/getYouLiuYanByPageInfoForSite")
    public Object getYouLiuYanByPageInfoForSite(PageInfo pageInfo) {
        try {
            pageInfo = liuYanService.getYouLiuYanByPageInfoForSite(pageInfo, null);
            return ResponseResult.createOK(pageInfo);
        } catch (BlogException e) {
            //e.printStackTrace();
            return ResponseResult.createFail(e.getMessage());
        }
    }


    /**
     * 网站后台的基本操作
     *
     * @param pageInfo
     * @return
     */
    @AccessLimit
    @GetMapping("/getYouLiuYanByPageInfo")
    public Object getYouLiuYanByPageInfo(PageInfo pageInfo) {
        try {
            pageInfo = liuYanService.getYouLiuYanByPageInfo(pageInfo, null);
            return ResponseResult.createOK(pageInfo);
        } catch (BlogException e) {
            //e.printStackTrace();
            return ResponseResult.createFail(e.getMessage());
        }
    }

    /**
     * 发表我们的留言的信息
     *
     * @param loginUserDto
     * @param commentId
     * @return
     */
    @AccessLimit
    @PostMapping("/publicComments")
    public Object publicComments(LoginUserDto loginUserDto,
                                 @RequestParam("commentId") String commentId) {
        try {
            Object o = liuYanService.publicComments(loginUserDto, commentId);
            return ResponseResult.createOK(o);
        } catch (BlogException e) {
            //e.printStackTrace();
            return ResponseResult.createFail(e.getMessage());
        }
    }


}
