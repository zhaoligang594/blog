package com.breakpoint.service.impl;

import com.breakpoint.dao.YouhuijuanMapper;
import com.breakpoint.dto.PageInfo;
import com.breakpoint.entity.Youhuijuan;
import com.breakpoint.exception.BlogException;
import com.breakpoint.service.YouHuiJuanService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 优惠卷的基本操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/04/08
 */
@Service
public class YouHuiJuanServiceImpl implements YouHuiJuanService {


    @Resource
    private YouhuijuanMapper youhuijuanMapper;

    @Cacheable(cacheNames = "yuhuijuan",cacheManager = "simpleCacheManager",key = "'youhuijuanCache'+#pageInfo.currentPage+''+#pageInfo.keyWords")
    @Override
    public PageInfo<Youhuijuan> getYouhuijuanBypage(PageInfo pageInfo, Object o) throws BlogException {

        int l = youhuijuanMapper.countYouhuijuanByPageInfo(null, pageInfo.getKeyWords());

        int pageTotal = l % pageInfo.getPageSize() == 0 ? l / pageInfo.getPageSize() : l / pageInfo.getPageSize() + 1;
        pageInfo.setPageTotal(pageTotal);

        pageInfo.setPageSize(60);

        List<Youhuijuan> youhuijuans = youhuijuanMapper.selectYouhuijuanByPageInfo(
                (pageInfo.getCurrentPage() - 1) * pageInfo.getPageSize(), pageInfo.getPageSize(), null, pageInfo.getKeyWords());
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
        pageInfo.setData(youhuijuans);

        return pageInfo;
    }
}
