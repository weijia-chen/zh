package com.weijia.zh.qa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

//线程池
@Configuration
public class ThreadPoolConfig {

    @Value("${ThreadPool.corePoolSize}")
    private Integer corePoolSize;
    @Value("${ThreadPool.maxPoolSize}")
    private Integer maxPoolSize;
    @Value("${ThreadPool.queueSize}")
    private Integer queueSize;
    @Value("${ThreadPool.keepTime}")
    private Integer keepTime;

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(){


        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(corePoolSize);// 核心线程池数
        pool.setKeepAliveSeconds(keepTime);
        pool.setMaxPoolSize(maxPoolSize);// 最大线程
        pool.setQueueCapacity(queueSize); // 队列容量
        pool.setThreadNamePrefix("zh_user");//设置线程池前缀前缀
        pool.setRejectedExecutionHandler(new java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy()); // 队列满，线程被拒绝执行策略
        return pool;
    }
}
