package com.nexign.task.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

@Configuration
public class KafkaReplyConfig {

    @Bean
    @DependsOn("producerFactory")
    public ReplyingKafkaTemplate<String, Object, Object> replyingKafkaTemplate(ProducerFactory<String, Object> producerFactory, ConcurrentMessageListenerContainer<String, Object> factory) {
        var retryTemplate = new ReplyingKafkaTemplate<>(producerFactory, factory);

        retryTemplate.setCorrelationHeaderName("task_id");

        return retryTemplate;
    }
}
