package com.breakpoint.service;

import com.breakpoint.dto.PageInfo;
import com.breakpoint.entity.Youhuijuan;
import com.breakpoint.exception.BlogException;

/**
 * 优惠卷的基本操作
 * @author :breakpoint/赵立刚
 * @date : 2019/04/08
 */
public interface YouHuiJuanService {

    /**
     * f返回优惠卷
     * @param pageInfo
     * @param o
     * @return
     * @throws BlogException
     */
    PageInfo<Youhuijuan> getYouhuijuanBypage(PageInfo pageInfo, Object o) throws BlogException;
}
