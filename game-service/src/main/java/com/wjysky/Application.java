package com.wjysky;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
@Slf4j
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.wjysky.feign.service"})
public class Application {

    public static void main(String[] args) {
        ConfigurableEnvironment env = SpringApplication.run(Application.class, args).getEnvironment();
        String serviceName = env.getProperty("spring.application.name"); // 服务名称
        String servicePort = env.getProperty("server.port"); // 服务端口
        String[] serviceActive = env.getActiveProfiles(); // 运行环境
        log.info("\n------------------------------------------------------------\n" +
                        "\n\t[{}]启动完毕，端口：[{}]，运行环境为：[{}]\n" +
                        "\n------------------------------------------------------------",
                serviceName, servicePort, serviceActive);
    }

}
