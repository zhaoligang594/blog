package com.android.app.dao;

import com.android.app.entity.UserTable;
import com.android.app.entity.UserTableExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserTable2Mapper {

    /**
     * 查询登陆的用户
     *
     * @param loginToken
     * @return
     */
    UserTable selectByLoginToken(@Param("loginToken") String loginToken);

}