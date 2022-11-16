package com.example.demo.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 异步线程池配置类
 * notice:Spring 底层采用了线程池来异步化操作，但是要注意使用 @Async 注解的时候，其底层是会使用一个无界队列的线程池，所以要注意 oom 异常的发生
 *
 * @Author yanzx
 * @Date 2022/11/16 23:16
 */
@Configuration
public class AsyncExecuteConfig extends AsyncConfigurerSupport {

    /**
     * corePoolSize 核心线程数；
     * maximumPoolSize 最大线程数；
     * keepAliveTime 线程活跃时间；
     * zaResizableCapacityLinkedBlockingQueue 基于 JDK 改造的可伸缩队列；
     * allowCoreThreadTimeOut 允许核心线程数超时后被回收；
     * preStartCoreThread 是否要在一开始就启动 corePoolSize 数量的线程数
     * preStartAllCoreThreads 是否要在一开始就启动 maximumPoolSize 数量的线程数
     *
     * @return ThreadPoolTaskExecutor
     */
    @Bean
    public ThreadPoolTaskExecutor asyncExecutor() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setCorePoolSize(10);
        threadPool.setMaxPoolSize(15);
        threadPool.setWaitForTasksToCompleteOnShutdown(true);
        threadPool.setAwaitTerminationSeconds(60 * 15);
        return threadPool;
    }

    @Override
    public Executor getAsyncExecutor() {
        return this.asyncExecutor();
    }
}
