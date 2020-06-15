package com.breakpoint.service.impl;

import com.breakpoint.dao.BbsTableMapper;
import com.breakpoint.dto.LoginUserDto;
import com.breakpoint.dto.PageInfo;
import com.breakpoint.dto.TopicInfo;
import com.breakpoint.entity.BbsTable;
import com.breakpoint.entity.BbsTableExample;
import com.breakpoint.exception.BlogException;
import com.breakpoint.service.BbsService;
import com.breakpoint.util.GenerateIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 网站论坛的基本操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/05/09
 */
@Slf4j
@Service
public class BbsServiceImpl implements BbsService {

    /**
     * bbs的基本dao的操作
     */
    @Resource
    private BbsTableMapper bbsTableMapper;


    /**
     * redis的
     */
    @Resource
    private RedisTemplate redisTemplate;



    /**
     * 插入一条论坛
     * @return
     * @throws BlogException
     */
    @Override
    public Object insert(String bbsTile, String bbsDesc, String bbsText, String bbsCategory, LoginUserDto loginUserDto) throws BlogException {

        if (StringUtils.isEmpty(bbsTile)) {
            log.error("文章标题不能为空");
            throw new BlogException("文章标题不能为空");
        }
        if (StringUtils.isEmpty(bbsDesc)) {
            log.error("文章描述不能为空");
            throw new BlogException("文章描述不能为空");
        }
        if (StringUtils.isEmpty(bbsText)) {
            log.error("文章内容不能为空");
            throw new BlogException("文章内容不能为空");
        }
        if (StringUtils.isEmpty(bbsCategory)) {
            log.error("文章类别不能为空");
            throw new BlogException("文章类别不能为空");
        }

        BbsTable bbsTable = new BbsTable();
        bbsTable.setBbsId(GenerateIDUtils.generateId());
        bbsTable.setBbsCategory(bbsCategory);
        bbsTable.setBbsTitle(bbsTile);
        bbsTable.setBbsDesc(bbsDesc);
        bbsTable.setBbsText(bbsText);
        bbsTable.setUserId(loginUserDto.getBlogUser().getUserNo());
        Date nowTime = new Date();
        bbsTable.setGmtCreate(nowTime);
        bbsTable.setGmtModified(nowTime);
        bbsTableMapper.insertSelective(bbsTable);
        return "SUCCESS";
    }

    @Override
    public List<BbsTable> selectByDateDesc() throws BlogException {
        /**
         * 基本操作
         */
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object topic_list = valueOperations.get("bbs_list");
        if (null == topic_list) {
            BbsTableExample bbsTableExample = new BbsTableExample();
            bbsTableExample.setOrderByClause("gmt_create desc");
            List<BbsTable> blogTopics = bbsTableMapper.selectByExampleWithBLOBs(bbsTableExample);
            valueOperations.set("bbs_list", blogTopics, 30, TimeUnit.MINUTES);
            return blogTopics;
        } else {
            return (List<BbsTable>) topic_list;
        }

    }

    @Override
    public BbsTable selectOneById(long bbsId) throws BlogException {
        /**
         * 返回操操作结果
         */
        return bbsTableMapper.selectByBbsId(bbsId);
    }

    /**
     *
     * @param bbsId
     * @return
     * @throws BlogException
     */
    @Override
    public TopicInfo<BbsTable> selectTopicInfoById(long bbsId) throws BlogException {

        TopicInfo<BbsTable> bbsTableTopicInfo = new TopicInfo<BbsTable>();

        /**
         * 查看当前的是否存在
         */
        BbsTable currentBbs = bbsTableMapper.selectByBbsId(bbsId);

        if (null != currentBbs) {
            BbsTable preTopic = bbsTableMapper.selectPreBbs(currentBbs);
            if (null == preTopic) {
                preTopic = new BbsTable();
                preTopic.setBbsId(bbsId);
                preTopic.setBbsTitle("没有更多");
            }
            BbsTable nextTopic = bbsTableMapper.selectNextBbs(currentBbs);
            if (null == nextTopic) {
                nextTopic = new BbsTable();
                nextTopic.setBbsId(bbsId);
                nextTopic.setBbsTitle("没有更多");
            }

            bbsTableTopicInfo.setNextTopic(nextTopic);
            bbsTableTopicInfo.setPreTopic(preTopic);
        }

        bbsTableTopicInfo.setCurrentTopic(currentBbs);

        return bbsTableTopicInfo;
    }



    @Cacheable(value = "bbsCache",cacheManager = "simpleCacheManager",key = "'bbsCache'+#pageInfo.currentPage+''+#pageInfo.topicType")
    @Override
    public PageInfo<BbsTable> getBbsByPageInfo(PageInfo pageInfo, LoginUserDto loginUserDto) throws BlogException {

        /**
         *
         */
        pageInfo.setPageSize(10);


        Long userId = null;


        if (null != loginUserDto) {

            userId = loginUserDto.getBlogUser().getUserNo();
        }

        /**
         * 查询文章的类型
         */
        String bbsType = pageInfo.getTopicType();

        if (StringUtils.isEmpty(bbsType)) {
            bbsType = null;
        }

        int l = bbsTableMapper.countBbsByPageInfo(userId, bbsType);


        int pageTotal = l % pageInfo.getPageSize() == 0 ? l / pageInfo.getPageSize() : l / pageInfo.getPageSize() + 1;
        pageInfo.setPageTotal(pageTotal);


        /**
         * 查询数据
         */
        List<BbsTable> blogTopics = bbsTableMapper.selectBbsByPageInfo(
                (pageInfo.getCurrentPage() - 1) * pageInfo.getPageSize(), pageInfo.getPageSize(), userId, bbsType);

        int prePage;
        int nextPage;

        if (pageInfo.getCurrentPage() <= 1) {
            prePage = 1;
            nextPage = 2;
        } else if (pageInfo.getPageTotal() <= pageInfo.getCurrentPage()) {
            prePage = pageInfo.getCurrentPage() - 1;
            nextPage = pageInfo.getCurrentPage();
        } else {
            prePage = pageInfo.getCurrentPage() - 1;
            nextPage = pageInfo.getCurrentPage() + 1;
        }
        pageInfo.setPrePage(prePage);
        pageInfo.setNextPage(nextPage);
        pageInfo.setData(blogTopics);
        return pageInfo;
    }

    @Override
    public Object updateBbs(Long bbsId, String bbsTile, String bbsDesc, String bbsText, String bbsCategory,
                            LoginUserDto loginUserDto) throws BlogException {

        if (StringUtils.isEmpty(bbsTile)) {
            log.error("文章标题不能为空");
            throw new BlogException("文章标题不能为空");
        }
        if (StringUtils.isEmpty(bbsDesc)) {
            log.error("文章描述不能为空");
            throw new BlogException("文章描述不能为空");
        }
        if (StringUtils.isEmpty(bbsText)) {
            log.error("文章内容不能为空");
            throw new BlogException("文章内容不能为空");
        }
        if (StringUtils.isEmpty(bbsCategory)) {
            log.error("文章类别不能为空");
            throw new BlogException("文章类别不能为空");
        }

        BbsTable bbsTable = bbsTableMapper.selectByBbsId(bbsId);

        if (null == bbsTable) {
            throw new BlogException("您更新的文章不存在！更新失败");
        } else {

            bbsTable.setBbsCategory(bbsCategory);
            bbsTable.setBbsTitle(bbsTile);
            bbsTable.setBbsDesc(bbsDesc);
            bbsTable.setBbsText(bbsText);
            //blogTopic.setUserId(loginUserDto.getBlogUser().getUserNo());
            Date nowTime = new Date();
            bbsTable.setGmtModified(nowTime);

            try {
                bbsTableMapper.updateByPrimaryKeyWithBLOBs(bbsTable);
            } catch (Exception e) {
                throw new BlogException("更新失败,请稍后再试");
            }

            return "OK";
        }

    }
}
