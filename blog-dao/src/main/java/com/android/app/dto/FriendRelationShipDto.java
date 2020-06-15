package com.android.app.dto;

import lombok.Data;

import java.util.Date;

/**
 * 朋友实体的基本列表
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/02/02
 */
@Data
public class FriendRelationShipDto {

    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户主键  用于线上的交流操作
     */
    private long userkey;

    /**
     * 好友的用户头像
     */
    private String friendImage;
    /**
     * 成为还有的时间
     */
    private Date relationDate;

}
