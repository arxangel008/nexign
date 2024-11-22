package com.nexign.task.client.service;

import com.nexign.task.common.model.TaskCreateRequestDto;
import com.nexign.task.common.model.TaskCreateResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.stereotype.Service;

import static java.time.Duration.ofSeconds;

/**
 * Сервис отправки сообщения в Кафку
 */
@Service
@RequiredArgsConstructor
public class TaskKafkaProducer {

    /**
     * Топик для отправки
     */
    @Value("${kafka.common.topic}")
    private String topic;

    private final ReplyingKafkaTemplate<String, TaskCreateRequestDto, TaskCreateResponseDto> replyingKafkaTemplate;

    /**
     * Отправка задания в кафку с ожиданием ответа
     *
     * @param taskCreateRequestDto информация о задании
     * @return ответ по созданному заданию
     */
    public TaskCreateResponseDto sendTaskWithReply(TaskCreateRequestDto taskCreateRequestDto) {
        try {
            ProducerRecord<String, TaskCreateRequestDto> record = new ProducerRecord<>(topic, taskCreateRequestDto);
            var future = replyingKafkaTemplate.sendAndReceive(record, ofSeconds(30));
            var response = future.get();

            if (response.value() == null) {
                throw new RuntimeException("Ошибка при создании задания");
            }

            return response.value();
        } catch (Exception e) {
            throw new RuntimeException("Возникла при ошибке отправке сообщения в Кафку", e);
        }
    }
}
