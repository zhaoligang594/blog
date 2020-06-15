package com.foodsite.food.service;

import com.breakpoint.dto.PageInfo;
import com.breakpoint.exception.FoodSiteException;
import com.foodsite.food.entity.FoodUser;

/**
 * 美食商城的基本操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/01/13
 */
public interface FoodShopService {

    /**
     * 查询所有美食商城的食物
     *
     * @param pageInfo
     * @return
     * @throws FoodSiteException
     */
    PageInfo searchAllByPage(PageInfo pageInfo, FoodUser foodUser,String foodType) throws FoodSiteException;


    /**
     * 增加新的数据 美食商品
     *
     * @param foodUser  登陆的操作者
     * @param fName     美食名称
     * @param fPhoto    美食图片
     * @param fDesc     美食描述
     * @param unitPrice 单价
     * @return
     * @throws FoodSiteException
     */
    Object insertBuyFood(FoodUser foodUser, String fName, String fPhoto, String fDesc, int unitPrice) throws FoodSiteException;
}
