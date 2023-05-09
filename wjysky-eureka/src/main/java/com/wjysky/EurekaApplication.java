package com.wjysky;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
@Slf4j
public class EurekaApplication {

    public static void main(String[] args) {
        log.info("-------------------------------------------------- " +
                "开始启动[wjysky-eureka]" +
                " --------------------------------------------------");
        SpringApplication.run(EurekaApplication.class, args);
        log.info("-------------------------------------------------- " +
                "[wjysky-eureka]启动完毕，端口：8761" +
                " --------------------------------------------------");
    }

}
