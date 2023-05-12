package com.wjysky.components.minio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * MinioProperties
 *
 * @author 王俊元（wangjunyuan@talkweb.com.cn）
 * @date 2023-03-09 16:33:28
 * @apiNote Minio存储服务配置信息
 */
@Data
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    private String url;

    private String accessKey;

    private String secretKey;

    private String bucket;
}
