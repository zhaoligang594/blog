package com.breakpoint.service;

import com.breakpoint.exception.BaseException;

/**
 * @author :breakpoint/赵立刚
 * @date : 2020/03/21
 */
public interface StatisticService {

    /**
     * 统计我们的文章的基本信息
     *
     * @return
     * @throws BaseException
     */
    Object satisticTopic() throws BaseException;

    /**
     * 统计我们的图片的基本信息
     *
     * @return
     * @throws BaseException
     */
    Object satisticPhotos() throws BaseException;

    /**
     * 统计留言信息
     *
     * @return
     * @throws BaseException
     */
    Object satisticComments() throws BaseException;


    Object getTodayDate() throws BaseException;


    Object getNewYear() throws BaseException;


}
