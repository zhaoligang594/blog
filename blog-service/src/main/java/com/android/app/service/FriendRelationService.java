package com.android.app.service;

import com.android.app.entity.UserTable;
import com.breakpoint.exception.AppException;
import com.breakpoint.exception.BaseException;

/**
 * 加好友之类的基本操作
 * 朋友关系的基本操作
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/02/02
 */
public interface FriendRelationService {

    /**
     * 获取用户的朋友关系
     *
     * @param userKey
     * @return
     * @throws AppException
     */
    Object getFriends(long userKey) throws BaseException;

    /**
     * 查询一个用户，并且添加添加好友
     *
     * @param userKey
     * @return
     * @throws AppException
     */
    Object searchFriend(String userKey) throws BaseException;

    /**
     * 添加好友的基本操作
     *
     * @param userTable
     * @param userKey
     * @return
     * @throws AppException
     */
    Object addFriend(UserTable userTable, long userTo) throws BaseException;

    /**
     * 删除好友
     *
     * @param userTable
     * @param userKey
     * @return
     * @throws AppException
     */
    Object deleteFriend(UserTable userTable, String userKey) throws BaseException;
}
