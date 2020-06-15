package com.netty.server.configue;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 * @author :breakpoint/赵立刚
 * @date : 2019/01/31
 */
@Component
@ConfigurationProperties(prefix = "netty")
public class NettyConfig {

    private int port;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
