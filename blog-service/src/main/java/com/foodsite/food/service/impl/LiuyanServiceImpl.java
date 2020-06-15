package com.foodsite.food.service.impl;

import com.breakpoint.dto.PageInfo;
import com.breakpoint.exception.FoodSiteException;
import com.breakpoint.util.GenerateIDUtils;
import com.foodsite.food.dao.LiuyanTableMapper;
import com.foodsite.food.entity.FoodUser;
import com.foodsite.food.entity.LiuyanTable;
import com.foodsite.food.service.LiuyanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 后台留言的操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/01/16
 */
@Slf4j
@Service
public class LiuyanServiceImpl implements LiuyanService {

    /**
     * 留言的基本操作
     */
    @Resource
    private LiuyanTableMapper liuyanTableMapper;

    /**
     * @param pageInfo 返回留言的基本信息
     * @param foodUser 登陆用户  如果没有登陆  也就是 网站整体后台的留言  供大家所有人观看
     * @return
     * @throws FoodSiteException
     */
    @Override
    public PageInfo<LiuyanTable> getLiuyanListByPage(PageInfo<LiuyanTable> pageInfo, FoodUser foodUser) throws FoodSiteException {
        int start = (pageInfo.getCurrentPage() - 1) * pageInfo.getPageSize();

        if (null == foodUser) {
            log.info("网站后台的访问  查询留言信息");
            List<LiuyanTable> liuyanTables = liuyanTableMapper.selectLiuYanListByPage(null, start, pageInfo.getPageSize());
            int totalCount = liuyanTableMapper.selectLiuYanListByPageCount(null);
            pageInfo.setData(liuyanTables);
            pageInfo.setPageTotalByTotalCount(totalCount);
        } else {
            log.info("用户查询自己的留言信息  查询留言信息");
            List<LiuyanTable> liuyanTables = liuyanTableMapper.selectLiuYanListByPage(foodUser.getId(), start, pageInfo.getPageSize());
            int totalCount = liuyanTableMapper.selectLiuYanListByPageCount(foodUser.getId());
            pageInfo.setData(liuyanTables);
            pageInfo.setPageTotalByTotalCount(totalCount);
        }
        return pageInfo;
    }


    /**
     * @param foodUser 登陆的用户
     * @param text     留言的基本内容
     * @return
     * @throws FoodSiteException
     */
    @Transactional(rollbackFor = FoodSiteException.class)
    @Override
    public Object insert(FoodUser foodUser, String text) throws FoodSiteException {
        /**
         * 进行存储操作
         */
        Date nowTime = new Date();

        LiuyanTable liuyanTable = new LiuyanTable();

        liuyanTable.setLiuyanId(GenerateIDUtils.generateId());

        liuyanTable.setLiuyanText(text);

        liuyanTable.setLiuyanUser(foodUser.getId());

        liuyanTable.setGmtCreate(nowTime);

        liuyanTable.setGmtUpdate(nowTime);

        try {
            /**
             * 进行存储数据
             */
            liuyanTableMapper.insert(liuyanTable);

            return true;

        } catch (Exception e) {

            throw new FoodSiteException("存储失败");

        }

    }
}
