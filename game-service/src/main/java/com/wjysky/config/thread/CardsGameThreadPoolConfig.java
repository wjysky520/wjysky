package com.wjysky.config.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
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
@Slf4j
public class CardsGameThreadPoolConfig implements AsyncConfigurer {

    /**
     * 核心线程数，IO密集型
     */
    public static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2;

    /**
     * 最大工作线程数
     */
    public static final int MAX_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 5;

    /**
     * 允许线程空闲时间（单位为秒）
     */
    public static final int KEEP_ALIVE_TIME = 10;

    /**
     * 缓冲队列数
     */
    public static final int QUEUE_CAPACITY = Runtime.getRuntime().availableProcessors() * 2;

    /**
     * 线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁
     */
    public static final int AWAIT_TERMINATION = 60;

    /**
     * 用来设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
     */
    public static final Boolean WAIT_COMPLETE_ON_SHUTDOWN = true;

    /**
     * 线程池名前缀
     */
    public static final String THREAD_NAME_PREFIX = "Async-Service-";

    @Bean("cardsGameTaskExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setKeepAliveSeconds(KEEP_ALIVE_TIME);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        executor.setWaitForTasksToCompleteOnShutdown(WAIT_COMPLETE_ON_SHUTDOWN);
        executor.setAwaitTerminationSeconds(AWAIT_TERMINATION);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (Throwable ex, Method method, Object... params) -> {
            log.error("class#method: " + method.getDeclaringClass().getName() + "#" + method.getName());
            log.error("type        : " + ex.getClass().getName());
            log.error("exception   : " + ex.getMessage());
            log.error("错误信息   : ", ex);
        };
    }
}
