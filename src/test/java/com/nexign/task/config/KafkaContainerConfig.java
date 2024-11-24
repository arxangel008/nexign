package com.nexign.task.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.KafkaContainer;

import static org.testcontainers.utility.DockerImageName.parse;

/**
 * Конфигурация кафка
 */
@TestConfiguration
public class KafkaContainerConfig {

    @Bean
    @ServiceConnection
    public KafkaContainer kafkaContainer() {
        return new KafkaContainer(parse("confluentinc/cp-kafka"));
    }
}
