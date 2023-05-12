package com.wjysky.components.redis.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * JedisConfig
 *
 * @author 王俊元（wangjunyuan@talkweb.com.cn）
 * @date 2023-03-17 14:36:22
 * @apiNote Redis服务配置信息
 */
@Configuration
@Slf4j
public class JedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout; // Redis服务器连接密码（默认为空）

    @Value("${spring.redis.password}")
    private String password; // 连接池中的最大连接数

    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive; // 连接池中的最大连接数，默认为8，使用负数表示没有限制

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle; // 默认连接数最大空闲的连接数，默认为8，使用负数表示没有限制

    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle; // 默认连接池最小空闲的连接数，默认为0，允许设置0和正数

    @Value("${spring.redis.jedis.pool.max-wait}")
    private int maxWait; // 连接池最大阻塞等待时间，单位：毫秒。默认为-1，表示不限制

    @Bean
    public JedisPool jedisPool(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(maxActive);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxWaitMillis(maxWait);
        JedisPool jedisPool = new JedisPool(poolConfig, host, port, timeout, password);
        log.info("JedisPool[{}:{}]连接成功", host, port);
        return jedisPool;
    }
}
