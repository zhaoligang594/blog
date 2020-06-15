package com.android.app.service.impl;

import com.android.app.dao.FriendRelation2Mapper;
import com.android.app.dao.FriendRelationMapper;
import com.android.app.dao.UserTableMapper;
import com.android.app.entity.FriendRelation;
import com.android.app.entity.UserTable;
import com.android.app.entity.UserTableExample;
import com.android.app.service.FriendRelationService;
import com.breakpoint.exception.AppException;
import com.breakpoint.exception.BaseException;
import com.breakpoint.util.LocalVerify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 具体的逻辑操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/02/02
 */
@Slf4j
@Service
public class FriendRelationServiceImpl implements FriendRelationService {

    /**
     * 数据库的基本操作
     */
    @Resource
    private FriendRelation2Mapper friendRelation2Mapper;

    @Resource
    private FriendRelationMapper friendRelationMapper;

    @Resource
    private UserTableMapper userTableMapper;

    /**
     * 获取用户的基本关系   查询自己
     *
     * @param userKey
     * @return
     * @throws AppException
     */
    @Override
    public Object getFriends(long userKey) throws BaseException {
        try {

            LocalVerify.verifyStringIsNotNall(userKey + "");
            /**
             * 查询基本操作
             */
            List<FriendRelation> allFriendByUserKey = friendRelation2Mapper.getAllFriendByUserKey(userKey);
            return allFriendByUserKey;
        } catch (Exception e) {
            throw new AppException(e.getMessage());
        }
    }

    /**
     * 查询好友
     *
     * @param userKey 用户名的基本操作
     * @return
     * @throws AppException
     */
    @Override
    public Object searchFriend(String userKey) throws BaseException {
        /**
         * 校验基本参数
         */
        LocalVerify.verifyStringIsNotNall(userKey);
        UserTableExample userTableExample = new UserTableExample();
        userTableExample.createCriteria().andUserNameEqualTo(userKey);
        try {
            List<UserTable> userTables = userTableMapper.selectByExample(userTableExample);
            if (null != userTables && userTables.size() > 0) {
                /**
                 * 返回查询到的用户
                 */
                return userTables.get(0);
            } else {
                throw new AppException("没有查询到数据");
            }
        } catch (Exception e) {
            throw new AppException("查询出错，请联系管理员");
        }
    }

    /**
     * 添加新用户
     *
     * @param userTable
     * @param userTo
     * @return
     * @throws AppException
     */
    @Override
    public Object addFriend(UserTable userTable, long userTo) throws BaseException {
        try {
            /**
             * 首先查询所有用户
             */
            List<FriendRelation> allFriendByUserKey = friendRelation2Mapper.getAllFriendByUserKey(userTable.getuId());
            if (null != allFriendByUserKey && allFriendByUserKey.size() > 0) {
                throw new AppException("该用户已经是你的好友");
            } else {
                /**
                 * 新建里关系
                 */
                FriendRelation friendRelation = new FriendRelation();
                friendRelation.setUserFrom(userTable.getuId());
                friendRelation.setUserTo(userTo);
                Date nowTime = new Date();
                friendRelation.setGmtCreate(nowTime);
                friendRelation.setGmtUpdate(nowTime);
                friendRelationMapper.insert(friendRelation);

                return "添加好友成功";

            }
        } catch (Exception e) {
            throw new AppException("添加好友失败，请稍后重试");

        }

    }


    /**
     * 删除好友的基本操作
     *
     * @param userTable
     * @param userKey
     * @return
     * @throws AppException
     */
    @Override
    public Object deleteFriend(UserTable userTable, String userKey) throws BaseException {





        return null;
    }


}
