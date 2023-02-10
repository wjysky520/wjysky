package com.wjysky;

import com.wjysky.config.netty.server.NettyServer;
import com.wjysky.config.netty.ws.NioWebSocketServer;
import com.wjysky.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@Slf4j
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.wjysky.feign.service"})
@MapperScan("com.wjysky.dao")
public class Application {

    public static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(Application.class, args);

        new Thread(() -> {
            try {
                NettyServer nettyServer = SpringUtil.getBean(NettyServer.class);
                int port = 7890;
                nettyServer.myInit("0.0.0.0", port);
                Runtime.getRuntime().addShutdownHook(new Thread(nettyServer::destroy));
            } catch (Exception e) {
                log.error("Netty服务开启时异常", e);
            }
        }).start();

        new Thread(() -> {
            try {
                NioWebSocketServer wsServer = SpringUtil.getBean(NioWebSocketServer.class);
                wsServer.init();
            } catch (Exception e) {
                log.error("NioWebsocket服务开启时异常", e);
            }
        }).start();
    }

}
