package com.wjysky.components.idgenerator.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * IdGeneratorProperties
 *
 * @author 王俊元（wangjunyuan@talkweb.com.cn）
 * @date 2023-05-11 17:31:28
 * @apiNote ID生成器配置信息
 */
@Data
@ConfigurationProperties(value = "idgenerator")
public class IdGeneratorProperties {

    /**
     * 终端ID
     */
    private Long workerId = 0L;

    /**
     * 数据中心ID
     */
    private Long dataCenterId = 0L;
}
