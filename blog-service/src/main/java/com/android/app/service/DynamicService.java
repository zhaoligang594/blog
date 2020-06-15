package com.android.app.service;

import com.android.app.entity.UserTable;
import com.breakpoint.dto.PageInfo;
import com.breakpoint.exception.AppException;
import com.breakpoint.exception.BaseException;

/**
 * 用户动态的基本操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/01/25
 */
public interface DynamicService {


    /**
     * 登陆用户查询
     *
     * @param pageInfo  请求页信息
     * @param dyType    发表的类别
     * @param userTable 登陆用户
     * @return
     * @throws AppException
     */
    Object searchByPage(PageInfo pageInfo, Integer dyType, UserTable userTable,Integer dyId) throws BaseException;


    /**
     * 新建发表
     *
     * @param dyTitle
     * @param dyText
     * @param dyType
     * @param userTable
     * @return
     * @throws AppException
     */
    Object insertOne(String dyTitle, String dyText, Integer dyType, UserTable userTable) throws BaseException;


}
