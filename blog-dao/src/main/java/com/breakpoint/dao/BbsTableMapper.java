package com.breakpoint.dao;

import com.breakpoint.entity.BbsTable;
import com.breakpoint.entity.BbsTableExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BbsTableMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bbs_table
     *
     * @mbg.generated
     */
    long countByExample(BbsTableExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bbs_table
     *
     * @mbg.generated
     */
    int deleteByExample(BbsTableExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bbs_table
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bbs_table
     *
     * @mbg.generated
     */
    int insert(BbsTable record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bbs_table
     *
     * @mbg.generated
     */
    int insertSelective(BbsTable record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bbs_table
     *
     * @mbg.generated
     */
    List<BbsTable> selectByExampleWithBLOBs(BbsTableExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bbs_table
     *
     * @mbg.generated
     */
    List<BbsTable> selectByExample(BbsTableExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bbs_table
     *
     * @mbg.generated
     */
    BbsTable selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bbs_table
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") BbsTable record, @Param("example") BbsTableExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bbs_table
     *
     * @mbg.generated
     */
    int updateByExampleWithBLOBs(@Param("record") BbsTable record, @Param("example") BbsTableExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bbs_table
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") BbsTable record, @Param("example") BbsTableExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bbs_table
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BbsTable record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bbs_table
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyWithBLOBs(BbsTable record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bbs_table
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(BbsTable record);



    /**
     * 自定义
     *
     * @return
     */
    BbsTable selectByBbsId(@Param("bbsId") long bbsId);

    /**
     * 查询下一个
     *
     * @param blogTopic
     * @return
     */
    BbsTable selectNextBbs(@Param("bbsTable") BbsTable bbsTable);

    /**
     * 查询上虞个
     *
     * @param blogTopic
     * @return
     */
    BbsTable selectPreBbs(@Param("bbsTable") BbsTable bbsTable);


    List<BbsTable> selectBbsByPageInfo(@Param("pageStart") int pageStart, @Param("pageSize") int pageSize,
                                          @Param("userId") Long userId,@Param("bbsType") String bbsType);

    int countBbsByPageInfo(@Param("userId") Long userId, @Param("bbsType") String bbsType);



}