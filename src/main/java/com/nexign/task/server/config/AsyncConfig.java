package com.nexign.task.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Конфигурирование ассинхронного взаимодействия
 */
@EnableAsync
@Configuration
public class AsyncConfig {

    /**
     * Ограничение на получение записей
     */
    @Value("${task.max-thread-to-processing}")
    private Integer maxThreadToProcessing;

    /**
     * Настройка Executor
     */
    @Bean
    public Executor taskExecutor() {
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(maxThreadToProcessing);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("task-processing-");
        executor.initialize();
        return executor;
    }
}
