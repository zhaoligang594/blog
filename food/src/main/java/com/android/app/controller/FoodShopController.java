package com.android.app.controller;

import com.breakpoint.annotation.AccessLimit;
import com.breakpoint.constans.FunctionEnum;
import com.breakpoint.constans.ResponseResult;
import com.breakpoint.dto.PageInfo;
import com.foodsite.food.entity.FoodUser;
import com.foodsite.food.service.FoodShopService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 美食商城的基本操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/01/13
 */
@RestController
@RequestMapping("/food/v1/shop/")
public class FoodShopController {

    @Resource
    private FoodShopService foodShopService;

    /**
     * 查找  美食商城的总体的查询
     *
     * @param pageInfo
     * @return
     */
    @AccessLimit(isLogIn = false)
    @GetMapping("/searchByPage")
    public Object searchAllByPage(PageInfo pageInfo,@RequestParam(value = "foodType",required = false) String foodType) {

        if (null == pageInfo) {
            return null;
        }

        try {

            PageInfo pageInfo1 = foodShopService.searchAllByPage(pageInfo, null,foodType);

            return ResponseResult.createOK(pageInfo1,null,FunctionEnum.美食商场);

        } catch (Exception e) {

            return ResponseResult.createFail(e.getMessage());
        }
    }

    /**
     * 用户查询自己的发布的商品
     *
     * @param pageInfo
     * @param foodUser
     * @return
     */
    @AccessLimit
    @GetMapping("/searchByPageAndUser")
    public Object searchAllByPagAndUser(PageInfo pageInfo, FoodUser foodUser,@RequestParam(value = "foodType",required = false) String foodType) {

        if (null == pageInfo) {
            return null;
        }

        if (null == foodUser) {
            return null;
        }

        try {

            PageInfo pageInfo1 = foodShopService.searchAllByPage(pageInfo, foodUser,foodType);

            return ResponseResult.createOK(pageInfo1);

        } catch (Exception e) {

            return ResponseResult.createFail(e.getMessage());
        }
    }


    //todo 商城的上传
    @AccessLimit
    @PostMapping("/insertBuyFood")
    public Object insertBuyFood(FoodUser foodUser, @RequestParam("fName") String fName,
                                @RequestParam("fPhoto") String fPhoto,
                                @RequestParam("fDesc") String fDesc) {
        /**
         * 用户没有登陆
         */
        if (null == foodUser) {
            return null;
        }


        try {

            Object o = foodShopService.insertBuyFood(foodUser, fName, fPhoto, fDesc, 1);
            return ResponseResult.createOK(o);
        } catch (Exception e) {
            return ResponseResult.createFail(e.getMessage());
        }

    }


}
