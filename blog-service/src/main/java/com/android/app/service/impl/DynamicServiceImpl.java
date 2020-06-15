package com.android.app.service.impl;

import com.android.app.dao.DynamicTable2Mapper;
import com.android.app.dao.DynamicTableMapper;
import com.android.app.dto.DynamicTableDto;
import com.android.app.entity.DynamicTable;
import com.android.app.entity.UserTable;
import com.android.app.service.DynamicService;
import com.breakpoint.dto.PageInfo;
import com.breakpoint.exception.AppException;
import com.breakpoint.exception.BaseException;
import com.breakpoint.util.GenerateIDUtils;
import com.breakpoint.util.LocalVerify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 用户动态的基本操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/01/25
 */
@Slf4j
@Service
public class DynamicServiceImpl implements DynamicService {

    /**
     * 动态的基本操作
     */
    @Resource
    private DynamicTableMapper dynamicTableMapper;

    @Resource
    private DynamicTable2Mapper dynamicTable2Mapper;


    /**
     * @param pageInfo  请求页信息
     * @param dyType    发表的类别
     * @param userTable 登陆用户
     * @return
     * @throws AppException
     */
    @Override
    public Object searchByPage(PageInfo pageInfo, Integer dyType, UserTable userTable, Integer dyId) throws BaseException {


        /**
         * 获取到当前页的基本信息
         */
        int currentPage = pageInfo.getCurrentPage();

        int pageSize = pageInfo.getPageSize();

        /**
         * 查询所有
         */
        if (null == userTable) {
            List<DynamicTableDto> dynamicTables = dynamicTable2Mapper.searchAllByPageAndUser(dyId, dyType, null,
                    (currentPage - 1) * pageSize, pageSize);
            int count = dynamicTable2Mapper.searchAllByPageAndUserCount(dyId, dyType, null);
            pageInfo.setPageTotalByTotalCount(count);
            pageInfo.setData(dynamicTables);

        } else {
            List<DynamicTableDto> dynamicTables = dynamicTable2Mapper.searchAllByPageAndUser(dyId, dyType, userTable.getId(),
                    (currentPage - 1) * pageSize, pageSize);
            int count = dynamicTable2Mapper.searchAllByPageAndUserCount(dyId, dyType, userTable.getId());
            pageInfo.setPageTotalByTotalCount(count);
            pageInfo.setData(dynamicTables);
        }


        return pageInfo;
    }


    /**
     * @param dyTitle
     * @param dyText
     * @param dyType
     * @param userTable
     * @return
     * @throws AppException
     */
    @Override
    public Object insertOne(String dyTitle, String dyText, Integer dyType, UserTable userTable) throws BaseException {

        LocalVerify.verifyStringIsNotNall(dyTitle, dyText);

        /**
         * 类别
         */
        if (null == dyType) {
            dyType = 0;
        }

        DynamicTable dynamicTable = new DynamicTable();
        dynamicTable.setDyId(GenerateIDUtils.generateId());
        dynamicTable.setDyType(dyType);
        dynamicTable.setDyText(dyText);
        dynamicTable.setDyTitle(dyTitle);
        dynamicTable.setuId(userTable.getId());

        Date nowTime = new Date();
        dynamicTable.setGmtCreate(nowTime);
        dynamicTable.setGmtUpdate(nowTime);

        try {
            dynamicTableMapper.insert(dynamicTable);
            return "OK";
        } catch (Exception e) {
            throw new AppException(e.getMessage());
        }


    }
}
