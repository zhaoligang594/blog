package com.breakpoint.service.impl;

import com.breakpoint.dao.BlogTopicMapper;
import com.breakpoint.dao.LiuyanMapper;
import com.breakpoint.dao.TbAlbumMapper;
import com.breakpoint.entity.TbAlbumExample;
import com.breakpoint.exception.BaseException;
import com.breakpoint.service.StatisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author :breakpoint/赵立刚
 * @date : 2020/03/21
 */
@Slf4j
@Service
public class StatisticServiceImpl implements StatisticService {

    /**
     * 注入我们的基本的操作
     */
    @Resource
    private BlogTopicMapper blogTopicMapper;

    @Resource
    private LiuyanMapper liuyanMapper;

    @Resource
    private TbAlbumMapper tbAlbumMapper;


    @Override
    public Object satisticTopic() throws BaseException {
        Map<String,Object> returnMap=new HashMap<>(3);
        returnMap.put("total",blogTopicMapper.selectTopicCount());
        returnMap.put("statistic",blogTopicMapper.selectTopicCountByType());
        returnMap.put("typeList",blogTopicMapper.selectTopicType());
        return returnMap;
    }

    @Override
    public Object satisticPhotos() throws BaseException {
        return tbAlbumMapper.countByExample(new TbAlbumExample());
    }

    @Override
    public Object satisticComments() throws BaseException {
        return liuyanMapper.countLiuyanByPageInfo(null);
    }

    @Override
    public Object getTodayDate() throws BaseException {

        Date nowTime=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd EEEE");
        String week = sdf.format(nowTime);
        Map<String,Object> map=new HashMap<>(2);
        map.put("today",nowTime);
        map.put("today2",week);
        return map;
    }

    @Override
    public Object getNewYear() throws BaseException {
        return null;
    }
}
