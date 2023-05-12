package com.wjysky.components.idgenerator.config;

import cn.hutool.core.lang.Snowflake;
import com.wjysky.components.idgenerator.IdGenerator;
import com.wjysky.components.idgenerator.MySnowflake;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * IdGeneratorConfig
 *
 * @author 王俊元（wangjunyuan@talkweb.com.cn）
 * @date 2023-05-11 17:31:28
 * @apiNote ID生成器配置类
 */
@Configuration
@EnableConfigurationProperties(IdGeneratorProperties.class)
public class IdGeneratorConfig {

    /**
     * @bizName ID生成器
     *
     * @title idGenerator
     * @apiNote ${todo}
     * @param properties ID生成器配置信息
     * @author 王俊元（wangjunyuan@talkweb.com.cn）
     * @date 2023/5/11 17:30
     * @return com.example.components.idgenerator.IdGenerator
     **/
    @Bean
    public IdGenerator idGenerator(IdGeneratorProperties properties) {
        Snowflake snowflake = new Snowflake(properties.getWorkerId(), properties.getDataCenterId());
        MySnowflake mySnowflake = new MySnowflake(properties.getWorkerId(), properties.getDataCenterId());
        return new IdGenerator(snowflake, mySnowflake);
    }

}
