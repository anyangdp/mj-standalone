package com.mj.activiti.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ActivitiConfig {
    @Bean
    @Primary
    public TaskExecutor primaryTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 配置TaskExecutor的其他属性
        // 核心线程池大小
        taskExecutor.setCorePoolSize(10);

        // 最大线程池大小
        taskExecutor.setMaxPoolSize(20);

        // 队列容量
        taskExecutor.setQueueCapacity(100);

        // 线程池中线程的前缀
        taskExecutor.setThreadNamePrefix("activiti-executor-");

        // 超过核心线程数的线程空闲时的存活时间
        taskExecutor.setKeepAliveSeconds(60);

        // 当线程池已满时，新任务的处理策略
        // ThreadPoolTaskExecutor.AbortPolicy: 默认策略，抛出RejectedExecutionException异常
        // ThreadPoolTaskExecutor.CallerRunsPolicy: 用调用线程来执行任务
        // ThreadPoolTaskExecutor.DiscardOldestPolicy: 抛弃队列中最旧的任务，然后再尝试执行新任务
        // ThreadPoolTaskExecutor.DiscardPolicy: 直接抛弃新任务
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

        // 是否允许核心线程超时退出，默认为false
        taskExecutor.setAllowCoreThreadTimeOut(false);
        return taskExecutor;
    }
}
