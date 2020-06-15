package com.netty.server.adapter;

import com.alibaba.fastjson.JSONObject;
import com.netty.server.user.UsersContext;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * 用户发送过来的信息
 * {"userkey":"sdgsdjsgdhjgshjgdjhasdh","message":"shaasgdhjgsahjgdhjsagjdh"}
 *
 * @author :breakpoint/赵立刚
 * @date : 2018/06/08
 */
public class FirstServerHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 消息过来后执行此方法
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + ":" + msg);
        /**
         * 得到了用户的发送的信息
         */
        JSONObject jsonObject = JSONObject.parseObject(msg);
        if (3 == jsonObject.getIntValue("type")) {
            /**
             * 用户注册
             */
            String userkey = jsonObject.getString("fromUserKey");
            UsersContext.addLoginUserContext(userkey, ctx);

        } else if (4 == jsonObject.getIntValue("type")) {
            /**
             * 用户退出操作
             */
            String userkey = jsonObject.getString("fromUserKey");
            UsersContext.removeLoginUserContext(userkey);


        } else if (2 == jsonObject.getIntValue("type")) {
            /**
             * 用户发送信息
             */

            String toUserKey = jsonObject.getString("toUserKey");
            String fromUserKey = jsonObject.getString("fromUserKey");

            ChannelHandlerContext loginUserContext = UsersContext.getLoginUserContext(toUserKey);


            /**
             * 发送给用户
             */
            if (null != loginUserContext) {

                try {
                    Channel channel = loginUserContext.channel();
                    if (channel.isActive()) {
                        loginUserContext.writeAndFlush(msg + "\r\n");
                    } else {
                        ctx.writeAndFlush("{\"toUserKey\":\""+toUserKey+"\",\"fromUserKey\":\""+fromUserKey+"\",\"respCode\": 300,\"type\": 2,\"message\": \"用户已下线,您发送的信息对方没有接收到\"}\n");
                        /**
                         * 用户移除
                         */
                        UsersContext.removeLoginUserContext(toUserKey);
                        return;
                    }


                } catch (Exception e) {
                    UsersContext.removeLoginUserContext(toUserKey);
                    System.out.println("已下线");
                    ctx.writeAndFlush("{\"toUserKey\":\""+toUserKey+"\",\"fromUserKey\":\""+fromUserKey+"\",\"respCode\": 300,\"type\": 2,\"message\": \"用户已下线,您发送的信息对方没有接收到\"}\n");
                    return;
                }

            } else {
                /**
                 * 这个用户没有登陆
                 */
                ctx.writeAndFlush("{\"toUserKey\":\""+toUserKey+"\",\"fromUserKey\":\""+fromUserKey+"\",\"respCode\": 300,\"type\": 2,\"message\": \"用户已下线,您发送的信息对方没有接收到\"}\n");
                return;
            }


        }
        //ctx.writeAndFlush("received your message:" + msg + "\r\n");
        ctx.writeAndFlush("{\"respCode\": 200,\"type\": 1,\"message\": \"操作成功\"}\n");
    }

    /**
     * 通道被客户端激活时执行此方法
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("RamoteAddress : " + ctx.channel().remoteAddress() + " active !");//通道激活
        /**
         * 用户登陆的key
         */
        String userKey = UUID.randomUUID().toString();
        //ctx.writeAndFlush("Welcome to " + InetAddress.getLocalHost().getHostName() + " service!\n");//回送进入服务系统

        ctx.writeAndFlush("{\"respCode\": 200,\"type\": 1,\"message\": \"操作成功\",\"userKey\":\"" + userKey + "\"}\n");//回送进入服务系统
    }
}
