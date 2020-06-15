package com.netty.server.adapter;


import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionHandler extends ChannelDuplexHandler {


    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ctx.write(msg, promise.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (!future.isSuccess()) {
                    log.info("ExceptionHandler", "send data to client exception occur: ");
                }
            }
        }));
    }
}

