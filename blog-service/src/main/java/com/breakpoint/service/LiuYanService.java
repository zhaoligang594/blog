package com.breakpoint.service;

import com.breakpoint.dto.LoginUserDto;
import com.breakpoint.dto.PageInfo;
import com.breakpoint.entity.Liuyan;
import com.breakpoint.exception.BlogException;

/**
 * @author :breakpoint/赵立刚
 * @date : 2019/04/09
 */
public interface LiuYanService {

    /**
     * 留言的基本操作
     *
     * @param userName
     * @param message
     * @return
     */
    Object insert(String userName, String message, String picureUrl) throws BlogException;


    /**
     * 网站的留言的基本操作
     *
     * @param pageInfo
     * @param o
     * @return
     * @throws BlogException
     */
    PageInfo<Liuyan> getYouLiuYanByPageInfoForSite(PageInfo pageInfo, Object o) throws BlogException;

    /**
     * 网站内部的操作
     *
     * @param pageInfo
     * @param o
     * @return
     * @throws BlogException
     */
    PageInfo<Liuyan> getYouLiuYanByPageInfo(PageInfo pageInfo, Object o) throws BlogException;

    /**
     * 发布我们的留言
     *
     * @param loginUserDto 当前的登陆用户
     * @param commentId    留言的ID
     * @return
     * @throws BlogException
     */
    Object publicComments(LoginUserDto loginUserDto, String commentId) throws BlogException;

}
