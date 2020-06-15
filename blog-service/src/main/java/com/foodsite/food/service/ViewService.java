package com.foodsite.food.service;

import com.breakpoint.dto.PageInfo;
import com.breakpoint.exception.FoodSiteException;

/**
 * 视图的基本操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/01/13
 */
public interface ViewService {

    /**
     * 查询美食的推荐  默认美食推荐9个
     * @param pageInfo  请求的基本信息
     * @return
     * @throws FoodSiteException
     */
    PageInfo searchAllTuiJianByPage(PageInfo pageInfo) throws FoodSiteException;
}
