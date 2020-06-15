package com.android.app.dao;

import com.android.app.dto.DynamicTableDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DynamicTable2Mapper {

    /**
     * 查询发表的动态
     *
     * @param dyType
     * @param userId
     * @param start
     * @param size
     * @return
     */
    List<DynamicTableDto> searchAllByPageAndUser(@Param("dyId") Integer dyId, @Param("dyType") Integer dyType, @Param("userId") Integer userId,
                                                 @Param("start") int start, @Param("size") int size);


    int searchAllByPageAndUserCount(@Param("dyId") Integer dyId, @Param("dyType") Integer dyType, @Param("userId") Integer userId);


}