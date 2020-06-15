package com.breakpoint.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author :breakpoint/赵立刚
 * @date : 2019/06/10
 */
@Data
public class BlogTopicDto implements Serializable {

    private Integer id;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long topicId;

    private String topicTitle;

    private String topicDesc;

    private String topicCategory;

    private Integer type;

    private String photoPath;

    private Long userId;

    private Date gmtCreate;

    private Date gmtModified;

    private String topicText;

    private String nickName;

    /**
     * 查询的时候限定的内容
     */
    private String searchTopicType;

    private String notSearchTopicType;

    private Integer seeCount;

}
