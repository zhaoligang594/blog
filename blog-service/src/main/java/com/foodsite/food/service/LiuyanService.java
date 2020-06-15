package com.foodsite.food.service;

import com.breakpoint.dto.PageInfo;
import com.breakpoint.exception.FoodSiteException;
import com.foodsite.food.entity.FoodUser;
import com.foodsite.food.entity.LiuyanTable;

/**
 * 网站后台的留言
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/01/16
 */
public interface LiuyanService {

    /**
     * 查询留言信息
     *
     * @param pageInfo 返回留言的基本信息
     * @param foodUser 登陆用户  如果没有登陆  也就是 网站整体后台的留言  供大家所有人观看
     * @return
     * @throws FoodSiteException
     */
    PageInfo<LiuyanTable> getLiuyanListByPage(PageInfo<LiuyanTable> pageInfo, FoodUser foodUser) throws FoodSiteException;


    /**
     * 后台的留言用户进行请求操作
     *
     * @param foodUser 登陆的用户
     * @param text     留言的基本内容
     * @return
     * @throws FoodSiteException
     */
    Object insert(FoodUser foodUser, String text) throws FoodSiteException;
}
