package com.nexign.task.server.config;

import com.nexign.task.common.model.TaskCreateRequestDto;
import com.nexign.task.common.model.TaskCreateResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaServerConfig {

    /**
     * Url до Кафки
     */
    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> consumerServerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return props;
    }

    @Bean
    public Map<String, Object> producerServerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return props;
    }

    @Bean
    public ConsumerFactory<String, TaskCreateRequestDto> requestConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerServerConfigs(), new StringDeserializer(), new JsonDeserializer<>(TaskCreateRequestDto.class));
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, TaskCreateRequestDto>> requestListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, TaskCreateRequestDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(requestConsumerFactory());
        factory.setReplyTemplate(replyTemplate());
        factory.setCorrelationHeaderName("task_id");
        return factory;
    }

    @Bean
    public ProducerFactory<String, TaskCreateResponseDto> replyProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerServerConfigs());
    }

    @Bean
    public KafkaTemplate<String, TaskCreateResponseDto> replyTemplate() {
        return new KafkaTemplate<>(replyProducerFactory());
    }
}
