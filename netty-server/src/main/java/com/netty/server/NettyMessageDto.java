package com.netty.server;

import lombok.Data;

/**
 * netty封装的对象
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/02/01
 */
@Data
public class NettyMessageDto {

    /**
     * 操作返回码  成功还是失败
     */
    private int respCode;

    /**
     * 消息的类型 1  服务器响应信息   2  用户发送的消息    3  用户注册信息用户注册在服务器
     */
    private int type;

    /**
     * 用户的认证  采用登陆时候的用户名
     */
    private String toUserKey;

    private String fromUserKey;

    /**
     * 用户发送的消息
     */
    private String message;

    /**
     * 消息类型  1  普通文本  2  照片
     */
    private int messageType;

    /**
     * 昵称
     */
    private String nickName;

    /**
     *
     */
    private int leftOrRight;

    /**
     * 默认构造方法
     */
    public NettyMessageDto() {
    }

    public NettyMessageDto(int respCode, int type, String toUserKey, String fromUserKey,String message, String nickName, int leftOrRight) {
        this.respCode = respCode;
        this.type = type;
        this.toUserKey = toUserKey;
        this.fromUserKey = fromUserKey;
        this.message = message;
        this.messageType = 1;
        this.nickName = nickName;
        this.leftOrRight = leftOrRight;
    }
}
