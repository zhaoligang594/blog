package com.breakpoint.service.impl;

import com.breakpoint.dao.BlogTopicMapper;
import com.breakpoint.dto.BlogTopicDto;
import com.breakpoint.dto.LoginUserDto;
import com.breakpoint.dto.PageInfo;
import com.breakpoint.dto.TopicInfo;
import com.breakpoint.entity.BlogTopic;
import com.breakpoint.entity.BlogTopicExample;
import com.breakpoint.exception.BaseException;
import com.breakpoint.exception.BlogException;
import com.breakpoint.service.TopicService;
import com.breakpoint.util.GenerateIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 博客的内容
 *
 * @author breakpoint/赵立刚
 * @date 2018/05/05
 */
@Slf4j
@Service
public class TopicServiceImpl implements TopicService {


    @Resource
    private BlogTopicMapper blogTopicMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Object insert(String topicTile, String topicDesc, String topicText, String topicCategory, LoginUserDto loginUserDto) throws BlogException {

        if (StringUtils.isEmpty(topicTile)) {
            log.error("文章标题不能为空");
            throw new BlogException("文章标题不能为空");
        }
        if (StringUtils.isEmpty(topicDesc)) {
            log.error("文章描述不能为空");
            throw new BlogException("文章描述不能为空");
        }
        if (StringUtils.isEmpty(topicText)) {
            log.error("文章内容不能为空");
            throw new BlogException("文章内容不能为空");
        }


        if (StringUtils.isEmpty(topicCategory)) {
            log.error("文章类别不能为空");
            throw new BlogException("文章类别不能为空");
        }


        BlogTopic blogTopic = new BlogTopic();
        blogTopic.setTopicId(GenerateIDUtils.generateId());
        blogTopic.setTopicCategory(topicCategory);
        blogTopic.setTopicTitle(topicTile);
        blogTopic.setTopicDesc(topicDesc);
        blogTopic.setTopicText(topicText);
        blogTopic.setSeeCount(0);
        //TODO  照片的操作
        blogTopic.setPhotoPath("images/blog-back1.png");
        blogTopic.setUserId(loginUserDto.getBlogUser().getUserNo());
        Date nowTime = new Date();
        blogTopic.setGmtCreate(nowTime);
        blogTopic.setGmtModified(nowTime);
        blogTopicMapper.insertSelective(blogTopic);
        return "SUCCESS";
    }

    @Override
    public List<BlogTopic> selectByDateDesc() throws BlogException {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object topic_list = valueOperations.get("topic_list");
        if (null == topic_list) {
            BlogTopicExample blogTopicExample = new BlogTopicExample();
            blogTopicExample.setOrderByClause("gmt_create desc");
            List<BlogTopic> blogTopics = blogTopicMapper.selectByExampleWithBLOBs(blogTopicExample);
            valueOperations.set("topic_list", blogTopics, 30, TimeUnit.MINUTES);
            return blogTopics;
        } else {
            return (List<BlogTopic>) topic_list;
        }
    }

    @Override
    public BlogTopicDto selectOneById(long topicId) throws BlogException {
        return blogTopicMapper.selectDtoByTopicId(topicId);
    }

    @Override
    public TopicInfo<BlogTopicDto> selectTopicInfoById(long topicId, String searchTopicType, String notSearchTopicType) throws BlogException {


        TopicInfo<BlogTopicDto> topicInfo = new TopicInfo<BlogTopicDto>();

        /**
         * 查看当前的是否存在
         */
        BlogTopicDto currentTopic = blogTopicMapper.selectDtoByTopicId(topicId);


        if (null != currentTopic) {

            currentTopic.setSeeCount(currentTopic.getSeeCount() == null ? 1 : currentTopic.getSeeCount() + 1);

            BlogTopic blogTopic = new BlogTopic();

            blogTopic.setId(currentTopic.getId());

            blogTopic.setSeeCount(currentTopic.getSeeCount());

            blogTopicMapper.updateByPrimaryKeySelective(blogTopic);

            if (StringUtils.isNotBlank(searchTopicType)) {
                currentTopic.setSearchTopicType(searchTopicType);
            }

//            if (StringUtils.isNotBlank(notSearchTopicType)) {
//                currentTopic.setNotSearchTopicType(notSearchTopicType);
//            }


            BlogTopicDto preTopic = blogTopicMapper.selectPreTopic(currentTopic);
            if (null == preTopic) {
                preTopic = new BlogTopicDto();
                preTopic.setTopicId(topicId);
                preTopic.setTopicTitle("没有更多");
            }
            BlogTopicDto nextTopic = blogTopicMapper.selectNextTopic(currentTopic);
            if (null == nextTopic) {
                nextTopic = new BlogTopicDto();
                nextTopic.setTopicId(topicId);
                nextTopic.setTopicTitle("没有更多");
            }

            topicInfo.setNextTopic(nextTopic);
            topicInfo.setPreTopic(preTopic);
        }

        topicInfo.setCurrentTopic(currentTopic);

        return topicInfo;


    }

    //@Cacheable(value = "topicCache",cacheManager = "simpleCacheManager",key = "'topicCache'+#pageInfo.currentPage+''+#pageInfo.topicType")
    @Override
    public PageInfo<BlogTopic> getTopicByPageInfo(PageInfo pageInfo, LoginUserDto loginUserDto) throws BlogException {

        /**
         *
         */
        //pageInfo.setPageSize(10);


        Long userId = null;


        if (null != loginUserDto) {

            userId = loginUserDto.getBlogUser().getUserNo();
        }

        /**
         * 查询文章的类型
         */
        String topicType = pageInfo.getTopicType();

        if (StringUtils.isEmpty(topicType)) {
            topicType = null;
        }

        List<BlogTopic> blogTopics1 = blogTopicMapper.selectTopicByPageInfoListPage(pageInfo, userId, topicType, null);

        pageInfo.setData(blogTopics1);

        return pageInfo;
    }

    /**
     * 更新topicid
     *
     * @return
     */
    @Override
    public Object updateTopic(Long topicId, String topicTile, String topicDesc, String topicText, String topicCategory,
                              LoginUserDto loginUserDto) throws BlogException {

        if (StringUtils.isEmpty(topicTile)) {
            log.error("文章标题不能为空");
            throw new BlogException("文章标题不能为空");
        }
        if (StringUtils.isEmpty(topicDesc)) {
            log.error("文章描述不能为空");
            throw new BlogException("文章描述不能为空");
        }
        if (StringUtils.isEmpty(topicText)) {
            log.error("文章内容不能为空");
            throw new BlogException("文章内容不能为空");
        }
        if (StringUtils.isEmpty(topicCategory)) {
            log.error("文章类别不能为空");
            throw new BlogException("文章类别不能为空");
        }

        BlogTopic blogTopic = blogTopicMapper.selectByTopicId(topicId);

        if (null == blogTopic) {
            throw new BlogException("您更新的文章不存在！更新失败");
        } else {

            blogTopic.setTopicCategory(topicCategory);
            blogTopic.setTopicTitle(topicTile);
            blogTopic.setTopicDesc(topicDesc);
            blogTopic.setTopicText(topicText);
            //blogTopic.setUserId(loginUserDto.getBlogUser().getUserNo());
            Date nowTime = new Date();
            blogTopic.setGmtModified(nowTime);

            try {
                blogTopicMapper.updateByPrimaryKeyWithBLOBs(blogTopic);
            } catch (Exception e) {
                throw new BlogException("更新失败,请稍后再试");
            }

            return "OK";
        }

    }


    @Override
    public Object getTop3Topic() throws BaseException {
        try {
            return blogTopicMapper.getTop3Topic();
        } catch (Exception e) {
            throw new BaseException("获取失败");
        }
    }

    @Override
    public Object getAllTopicType() {
        return blogTopicMapper.getAllTopicType();
    }
}
