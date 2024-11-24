package com.nexign.task.emulationclient.config;

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
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Конфигурация клиента кафки
 */
@Configuration
@RequiredArgsConstructor
public class KafkaClientConfig {

    /**
     * Url до Кафки
     */
    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * Топик ответа
     */
    @Value("${kafka.reply.topic}")
    private String replyTopic;

    /**
     * Наименование заголовка корреляции
     */
    @Value("${kafka.reply.correlation-header-name}")
    private String correlationHeaderName;

    /**
     * Наименование группы ответа
     */
    @Value("${kafka.reply.group-id}")
    private String groupId;

    @Bean
    public Map<String, Object> consumerClientConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return props;
    }

    @Bean
    public Map<String, Object> producerClientConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String, TaskCreateRequestDto> requestProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerClientConfigs());
    }

    @Bean
    public ConsumerFactory<String, TaskCreateResponseDto> replyConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerClientConfigs(), new StringDeserializer(), new JsonDeserializer<>(TaskCreateResponseDto.class));
    }

    @Bean
    public KafkaMessageListenerContainer<String, TaskCreateResponseDto> replyListenerContainer() {
        ContainerProperties containerProperties = new ContainerProperties(replyTopic);
        containerProperties.setGroupId(groupId);
        return new KafkaMessageListenerContainer<>(replyConsumerFactory(), containerProperties);
    }

    @Bean
    public ReplyingKafkaTemplate<String, TaskCreateRequestDto, TaskCreateResponseDto> replyKafkaTemplate(ProducerFactory<String, TaskCreateRequestDto> pf, KafkaMessageListenerContainer<String, TaskCreateResponseDto> lc) {
        var replyingKafkaTemplate = new ReplyingKafkaTemplate<>(pf, lc);
        replyingKafkaTemplate.setCorrelationHeaderName(correlationHeaderName);

        return replyingKafkaTemplate;
    }
}
