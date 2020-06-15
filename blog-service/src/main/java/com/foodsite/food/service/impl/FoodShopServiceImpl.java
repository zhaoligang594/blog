package com.foodsite.food.service.impl;

import com.breakpoint.dto.PageInfo;
import com.breakpoint.exception.FoodSiteException;
import com.breakpoint.util.GenerateIDUtils;
import com.foodsite.food.dao.BuyfoodMapper;
import com.foodsite.food.entity.Buyfood;
import com.foodsite.food.entity.FoodUser;
import com.foodsite.food.service.FoodShopService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 美食商城的基本操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/01/13
 */
@Service
public class FoodShopServiceImpl implements FoodShopService {

    /**
     * 商品表的基本操作
     */
    @Resource
    private BuyfoodMapper buyfoodMapper;

    /**
     * @param pageInfo
     * @return
     * @throws FoodSiteException
     */
    @Override
    public PageInfo searchAllByPage(PageInfo pageInfo, FoodUser foodUser, String foodType) throws FoodSiteException {

        if (null == pageInfo) {
            throw new FoodSiteException("null == pageInfo");
        }

        /**
         * 查询所有
         */
        if ("".equals(foodType)) {
            foodType = null;
        }


        int pageSize = pageInfo.getPageSize();
        int currentPage = pageInfo.getCurrentPage();

        int start = (currentPage - 1) * pageSize;

        /**
         * 分为 个人用户查询和统一查询
         */

        if (null == foodUser) {
            int totalCount = buyfoodMapper.selectAllCount(null, foodType);
            List<Buyfood> buyfoods = buyfoodMapper.selectByStartAndSize(null, start, pageSize, foodType);
            pageInfo.setPageTotalByTotalCount(totalCount);
            pageInfo.setData(buyfoods);
        } else {
            int totalCount = buyfoodMapper.selectAllCount(foodUser.getId(), foodType);
            List<Buyfood> buyfoods = buyfoodMapper.selectByStartAndSize(foodUser.getId(), start, pageSize, foodType);
            pageInfo.setPageTotalByTotalCount(totalCount);
            pageInfo.setData(buyfoods);
        }

        return pageInfo;
    }


    /**
     * @param foodUser
     * @return
     * @throws FoodSiteException
     */
    @Transactional
    @Override
    public Object insertBuyFood(FoodUser foodUser, String fName, String fPhoto, String fDesc, int unitPrice) throws FoodSiteException {


        /**
         * 检验基本数据
         */
        if (StringUtils.isEmpty(fName)) {
            throw new FoodSiteException("fName==null");
        }

        if (StringUtils.isEmpty(fPhoto)) {
            throw new FoodSiteException("fPhoto==null");
        }


        if (StringUtils.isEmpty(fDesc)) {
            throw new FoodSiteException("fDesc==null");
        }

        if (unitPrice <= 0) {
            throw new FoodSiteException("unitPrice <= 0");
        }


        Buyfood buyfood = new Buyfood();

        buyfood.setBfId(GenerateIDUtils.generateId());

        buyfood.setfName(fName);

        buyfood.setfPhoto(fPhoto);

        buyfood.setfDesc(fDesc);

        buyfood.setuId(foodUser.getId());

        buyfood.setUnitPrice(unitPrice);
        Date nowTime = new Date();
        buyfood.setGmtCreate(nowTime);
        buyfood.setGmtUpdate(nowTime);

        try {

            buyfoodMapper.insert(buyfood);
            return "OK";

        } catch (Exception e) {
            throw new FoodSiteException("插入数据失败");
        }

    }
}
