package com.quiz.quotamanage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ExecutorConfig {

    @Bean("threadPoolExecutor")
    public ThreadPoolExecutor getThreadPoolExecutor() {
        return new ThreadPoolExecutor(10, 20, 60, java.util.concurrent.TimeUnit.SECONDS,
                new java.util.concurrent.LinkedBlockingQueue<>());
    }

}
