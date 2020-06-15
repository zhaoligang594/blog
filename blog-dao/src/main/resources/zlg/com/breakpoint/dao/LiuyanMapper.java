package com.breakpoint.dao;

import com.breakpoint.entity.Liuyan;
import com.breakpoint.entity.LiuyanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LiuyanMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table liuyan
     *
     * @mbg.generated
     */
    long countByExample(LiuyanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table liuyan
     *
     * @mbg.generated
     */
    int deleteByExample(LiuyanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table liuyan
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table liuyan
     *
     * @mbg.generated
     */
    int insert(Liuyan record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table liuyan
     *
     * @mbg.generated
     */
    int insertSelective(Liuyan record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table liuyan
     *
     * @mbg.generated
     */
    List<Liuyan> selectByExample(LiuyanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table liuyan
     *
     * @mbg.generated
     */
    Liuyan selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table liuyan
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Liuyan record, @Param("example") LiuyanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table liuyan
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Liuyan record, @Param("example") LiuyanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table liuyan
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Liuyan record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table liuyan
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Liuyan record);
}