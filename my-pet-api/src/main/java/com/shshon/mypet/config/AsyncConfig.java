package com.shshon.mypet.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // 기본 실행 대기하는 Thread의 수
        executor.setMaxPoolSize(10); // 동시 동작하는 최대 Thread의 수
        executor.setQueueCapacity(10); // MaxPoolSize 초과 요청에서 Thread 생성 요청시, 해당 요청을 Queue에 저장하는데 이때 최대 수용 가능한 Queue의 수
        executor.setThreadNamePrefix("MY-PET-ASYNC-");
        executor.initialize();
        return executor;
    }
}
