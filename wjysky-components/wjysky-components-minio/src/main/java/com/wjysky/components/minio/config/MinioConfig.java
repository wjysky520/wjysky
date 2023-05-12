package com.wjysky.components.minio.config;

import com.wjysky.components.minio.utils.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MinioConfig
 *
 * @author 王俊元（wangjunyuan@talkweb.com.cn）
 * @date 2023-05-11 18:12:07
 * @apiNote minio存储服务配置类
 */

@Configuration
@EnableConfigurationProperties(MinioProperties.class)
@Slf4j
public class MinioConfig {

    @Bean
    public MinioUtil minioUtil(MinioProperties properties) {
        MinioUtil minioUtil = new MinioUtil(properties);
        try {
            minioUtil.init();
        } catch (Exception e) {
            log.error("Minio存储服务初始化失败", e);
        }
        return minioUtil;
    }
}
