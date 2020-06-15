package com.netty.server.listenner;

import com.netty.server.adapter.ExceptionHandler;
import com.netty.server.adapter.FirstServerHandler;
import com.netty.server.adapter.NettyServerHandler;
import com.netty.server.configue.NettyConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.nio.charset.Charset;

/**
 * @author :breakpoint/赵立刚
 * @date : 2019/01/31
 */
@Component("nettyServerListener")
public class NettyServerListener implements InitializingBean {


    public NettyServerListener() {

        System.out.println("NettyServerListener");
    }

    /**
     * NettyServerListener 日志输出器
     *
     * @author 叶云轩 create by 2017/10/31 18:05
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServerListener.class);
    /**
     * 创建bootstrap
     */
    ServerBootstrap serverBootstrap = new ServerBootstrap();
    /**
     * BOSS
     */
    EventLoopGroup boss = new NioEventLoopGroup();
    /**
     * Worker
     */
    EventLoopGroup work = new NioEventLoopGroup();
    /**
     * 通道适配器
     */
    //@Resource
    // private ServerChannelHandlerAdapter channelHandlerAdapter;
    /**
     * NETT服务器配置类
     */
    @Resource
    private NettyConfig nettyConfig;

    /**
     * 关闭服务器方法
     */
    @PreDestroy
    public void close() {
        LOGGER.info("关闭服务器....");
        //优雅退出
        boss.shutdownGracefully();
        work.shutdownGracefully();
    }

    /**
     * 开启及服务线程
     */
    @PostConstruct
    public void start() {
        // 从配置文件中(application.yml)获取服务端监听端口号
        int port = nettyConfig.getPort();
        serverBootstrap.group(boss, work)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 100)
                .handler(new LoggingHandler(LogLevel.INFO));
        try {
            //设置事件处理
            serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
//                    pipeline.addLast(new LengthFieldBasedFrameDecoder(NettyConstant.getMaxFrameLength()
//                            , 0, 2, 0, 2));
//                    pipeline.addLast(new LengthFieldPrepender(2));
                    pipeline.addLast("split",new DelimiterBasedFrameDecoder(1048576, Delimiters.lineDelimiter()));
                    pipeline.addLast("decoder",new StringDecoder(Charset.forName("UTF-8")));
                    pipeline.addLast("encoder",new StringEncoder(Charset.forName("UTF-8")));
                    pipeline.addLast("hander",new FirstServerHandler());
                    pipeline.addLast(new ExceptionHandler());
                    // pipeline.addLast(channelHandlerAdapter);
                }
            });
            LOGGER.info("netty服务器在[{}]端口启动监听", port);
            ChannelFuture f = serverBootstrap.bind(port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            LOGGER.info("[出现异常] 释放资源");
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet");
        start();
    }
}
