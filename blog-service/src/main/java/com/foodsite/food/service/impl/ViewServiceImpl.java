package com.foodsite.food.service.impl;

import com.breakpoint.dto.PageInfo;
import com.breakpoint.exception.FoodSiteException;
import com.foodsite.food.service.ViewService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :breakpoint/赵立刚
 * @date : 2019/01/13
 */
@Service
public class ViewServiceImpl implements ViewService {



    /**
     * @param pageInfo 请求的基本信息
     * @return
     * @throws FoodSiteException
     */
    @Override
    public PageInfo searchAllTuiJianByPage(PageInfo pageInfo) throws FoodSiteException {

        System.out.println(pageInfo);

        if(null==pageInfo){
           throw new FoodSiteException("null==pageInfo");
        }

        List list=new ArrayList();


        list.add(new Object());
        list.add(new Object());
        list.add(new Object());
        list.add(new Object());
        list.add(new Object());
        list.add(new Object());
        list.add(new Object());
        list.add(new Object());
        list.add(new Object());
        //list.add(new Object());

        pageInfo.setData(list);
        pageInfo.setPageTotal(9);
        System.out.println(pageInfo);

        return pageInfo;

    }
}
