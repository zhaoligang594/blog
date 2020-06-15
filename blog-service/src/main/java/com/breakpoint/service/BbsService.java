package com.breakpoint.service;

import com.breakpoint.dto.LoginUserDto;
import com.breakpoint.dto.PageInfo;
import com.breakpoint.dto.TopicInfo;
import com.breakpoint.entity.BbsTable;
import com.breakpoint.exception.BlogException;

import java.util.List;

/**
 * 网站论坛的基本擦坐
 * @author :breakpoint/赵立刚
 * @date : 2019/05/09
 */
public interface BbsService {

    /**
     * 论坛的内容
     *
     * @return
     * @throws BlogException
     */
    Object insert(String bbsTile, String bbsDesc, String bbsText, String bbsCategory, LoginUserDto loginUserDto) throws BlogException;


    /**
     * 查询所有
     *
     * @return
     * @throws BlogException
     */
    List<BbsTable> selectByDateDesc() throws BlogException;


    /**
     * 查看单个
     *
     * @param topicId
     * @return
     * @throws BlogException
     */
    BbsTable selectOneById(long bbsId) throws BlogException;

    /**
     * 查询结果
     *
     * @param topicId
     * @return
     * @throws BlogException
     */
    TopicInfo<BbsTable> selectTopicInfoById(long bbsId) throws BlogException;

    /**
     * 分页查询
     *
     * @param pageInfo
     * @return
     * @throws BlogException
     */
    PageInfo<BbsTable> getBbsByPageInfo(PageInfo pageInfo,LoginUserDto loginUserDto) throws BlogException;

    /**
     * 更新文章数据
     *
     * @return
     */
    Object updateBbs(Long bbsId, String bbsTile, String bbsDesc, String bbsText, String bbsCategory,
                       LoginUserDto loginUserDto) throws BlogException;




}
