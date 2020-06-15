package com.android.app.dao;


import com.android.app.entity.FriendRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户关系的基本操作
 */
public interface FriendRelation2Mapper {

    /**
     * 获取到用户列表
     *
     * @param userKey
     * @return
     */
    List<FriendRelation> getAllFriendByUserKey(@Param("userKey") long userKey);


}