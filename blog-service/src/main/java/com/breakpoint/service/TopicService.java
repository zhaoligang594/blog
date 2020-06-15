package com.breakpoint.service;

import com.breakpoint.dto.BlogTopicDto;
import com.breakpoint.dto.LoginUserDto;
import com.breakpoint.dto.PageInfo;
import com.breakpoint.dto.TopicInfo;
import com.breakpoint.entity.BlogTopic;
import com.breakpoint.exception.BaseException;
import com.breakpoint.exception.BlogException;

import java.util.List;

/**
 * 博客的内容
 *
 * @author breakpoint/赵立刚
 * @date 2018/05/05
 */
public interface TopicService {

    /**
     * 插入一个博客
     *
     * @param topicTile
     * @param topicDesc
     * @param topicText
     * @param topicCategory
     * @param loginUserDto
     * @return
     * @throws BlogException
     */
    Object insert(String topicTile, String topicDesc, String topicText, String topicCategory, LoginUserDto loginUserDto) throws BlogException;


    /**
     * 查询所有
     *
     * @return
     * @throws BlogException
     */
    List<BlogTopic> selectByDateDesc() throws BlogException;


    /**
     * 查看单个
     *
     * @param topicId
     * @return
     * @throws BlogException
     */
    BlogTopicDto selectOneById(long topicId) throws BlogException;

    /**
     * 查询结果
     *
     * @param topicId
     * @return
     * @throws BlogException
     */
    TopicInfo<BlogTopicDto> selectTopicInfoById(long topicId, String searchTopicType, String notSearchTopicType) throws BlogException;

    /**
     * 分页查询
     *
     * @param pageInfo
     * @return
     * @throws BlogException
     */
    PageInfo<BlogTopic> getTopicByPageInfo(PageInfo pageInfo, LoginUserDto loginUserDto) throws BlogException;

    /**
     * 更新文章数据
     *
     * @return
     */
    Object updateTopic(Long topicId, String topicTile, String topicDesc, String topicText, String topicCategory,
                       LoginUserDto loginUserDto) throws BlogException;

    /**
     * 获取到前三阅读亮的文章
     *
     * @param loginUserDto
     * @return
     * @throws BaseException
     */
    Object getTop3Topic() throws BaseException;

    /**
     * 获取到所有的文章的类别 之后在进行其他的操作
     *
     * @return
     */
    Object getAllTopicType();
}
