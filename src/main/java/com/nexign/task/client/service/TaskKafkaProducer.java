package com.nexign.task.client.service;

import com.nexign.task.common.model.TaskCreateRequestDto;
import com.nexign.task.common.model.TaskCreateResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Service;

import static java.time.Duration.ofSeconds;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskKafkaProducer {

    /**
     * Url до Кафки
     */
    @Value("${kafka.topic.callback}")
    private String topicCallback;

    private final ReplyingKafkaTemplate<String, TaskCreateRequestDto, TaskCreateResponseDto> replyingKafkaTemplate;

    public TaskCreateResponseDto sendTaskWithReply(TaskCreateRequestDto taskDTO) {
        // Создаем сообщение с заголовком correlationId
        ProducerRecord<String, TaskCreateRequestDto> record = new ProducerRecord<>("tasks", taskDTO);
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, topicCallback.getBytes()));


        try {
            // Отправляем сообщение и ждём ответа
            RequestReplyFuture<String, TaskCreateRequestDto, TaskCreateResponseDto> future = replyingKafkaTemplate.sendAndReceive(record, ofSeconds(30));
            ConsumerRecord<String, TaskCreateResponseDto> response = future.get(); // Ждём до 10 секунд

            if (response.value() == null) {
                throw new RuntimeException("Ошибка при создании задания");
            }

            return response.value();
        } catch (Exception e) {
            throw new RuntimeException("Failed to send task with reply", e);
        }
    }
}
