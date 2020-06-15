package com.android.app.controller;

import com.breakpoint.annotation.AccessLimit;
import com.breakpoint.constans.FunctionEnum;
import com.breakpoint.constans.ResponseResult;
import com.breakpoint.dto.PageInfo;
import com.breakpoint.exception.FoodSiteException;
import com.foodsite.food.entity.FoodUser;
import com.foodsite.food.service.LiuyanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 留言基本操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/01/16  留言的基本操作
 */
@Slf4j
@RestController
@RequestMapping("/food/v1/liuyan")
public class LiuYanController {

    /**
     * 留言基本操作
     */
    @Resource
    private LiuyanService liuyanService;


    /**
     * 网站后台的查询
     *
     * @param pageInfo
     * @return
     */
    @AccessLimit(isLogIn = false)
    @GetMapping("/getSiteLiuyan")
    public Object getSiteLiuyan(PageInfo pageInfo) {

        try {
            /**
             * 查询的基本操作
             */
            PageInfo liuyanListByPage = liuyanService.getLiuyanListByPage(pageInfo, null);
            /**
             * 网站的留言的基本操作
             */
            return ResponseResult.createOK(liuyanListByPage, null,FunctionEnum.网站的留言);
        } catch (FoodSiteException e) {
            /**
             * 返回失败的
             */
            return ResponseResult.createFail(e.getMessage());
        }


    }

    /**
     * 用户查询自己的留言
     *
     * @param pageInfo
     * @param foodUser
     * @return
     */
    @AccessLimit
    @GetMapping("/getSpaceLiuyan")
    public Object getSpaceLiuyan(PageInfo pageInfo, FoodUser foodUser) {

        if (null == foodUser) {
            return null;
        }

        try {
            /**
             * 查询的基本操作
             */
            PageInfo liuyanListByPage = liuyanService.getLiuyanListByPage(pageInfo, foodUser);
            return ResponseResult.createOK(liuyanListByPage);
        } catch (FoodSiteException e) {
            /**
             * 返回失败的
             */
            return ResponseResult.createFail(e.getMessage());
        }


    }

    /**
     * 进行留言操作
     * <p>
     * <p>
     * 验证验证码
     *
     * @param foodUser
     * @param text
     * @return
     */
    @AccessLimit
    @PostMapping("/insert")
    public Object insert(FoodUser foodUser, @RequestParam("text") String text) {
        try {

            Object insert = liuyanService.insert(foodUser, text);

            return ResponseResult.createOK(insert);

        } catch (FoodSiteException e) {


            return ResponseResult.createFail(e.getMessage());
        }

    }


}
