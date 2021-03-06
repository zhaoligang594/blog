package com.android.app.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DynamicTableDto implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dynamic_table.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dynamic_table.dy_id
     *
     * @mbg.generated
     */
    private Long dyId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dynamic_table.dy_title
     *
     * @mbg.generated
     */
    private String dyTitle;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dynamic_table.dy_type
     *
     * @mbg.generated
     */
    private Integer dyType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dynamic_table.u_id
     *
     * @mbg.generated
     */
    private Integer uId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dynamic_table.gmt_create
     *
     * @mbg.generated
     */
    private Date gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dynamic_table.gmt_update
     *
     * @mbg.generated
     */
    private Date gmtUpdate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dynamic_table.dy_text
     *
     * @mbg.generated
     */
    private String dyText;

    private String nickName;

}