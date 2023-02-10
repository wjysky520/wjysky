package com.wjysky.config.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName : ThreadPoolConfig
 * @Description : 数据相关的任务线程池配置
 * @company : 杭州远昊科技有限公司
 * @Author : 王俊元（wangjunyuan@newhow.com.cn）
 * @Date: 2020-10-27 09:45
 */
@EnableAsync
@Configuration
public class CardsGameThreadPoolConfig {

//    @Value("${task.pool.corePoolSize}")
    private int corePoolSize; // 核心线程数

//    @Value("${task.pool.maxPoolSize}")
    private int maxPoolSize; // 最大线程数

//    @Value("${task.pool.queueCapacity}")
    private int queueCapacity; // 队列中最大的数目

//    @Value("${task.pool.keepAliveSeconds}")
    private int keepAliveSeconds; // 线程空闲后的最大存活时间

    @Bean("cardsGameTaskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        corePoolSize = Runtime.getRuntime().availableProcessors(); //CPU密集型，因此用CPU核心数
        executor.setCorePoolSize(corePoolSize);
        maxPoolSize = corePoolSize * 4;
        executor.setMaxPoolSize(maxPoolSize);
        queueCapacity = 1024;
        executor.setQueueCapacity(queueCapacity);
        keepAliveSeconds = 60;
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix("CardsGameTaskExecutor-"); // 线程名称前缀
        // setRejectedExecutionHandler：当pool已经达到max size的时候，如何处理新任务
        // CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
