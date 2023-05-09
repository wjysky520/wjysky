package com.wjysky;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
@EnableEurekaServer
@Slf4j
public class EurekaApplication {

    public static void main(String[] args) {
        ConfigurableEnvironment env = SpringApplication.run(EurekaApplication.class, args).getEnvironment();
        String serviceName = env.getProperty("spring.application.name"); // 服务名称
        String servicePort = env.getProperty("server.port"); // 服务端口
        String[] serviceActive = env.getActiveProfiles(); // 运行环境
        log.info("\n------------------------------------------------------------\n" +
                "\n\t[{}]启动完毕，端口：[{}]，运行环境为：[{}]\n" +
                "\n------------------------------------------------------------",
                serviceName, servicePort, serviceActive);
    }

}
