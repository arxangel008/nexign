package com.nexign.task.integration.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexign.task.model.dto.TaskCreateRequestDto;
import com.nexign.task.model.dto.TaskCreateResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskKafkaProducer {

    private final ReplyingKafkaTemplate<String, Object, Object> replyingKafkaTemplate;
    private final ObjectMapper objectMapper;

    public TaskCreateResponseDto sendTaskWithReply(TaskCreateRequestDto taskDTO) {
        // Создаем сообщение с заголовком correlationId
        ProducerRecord<String, Object> record = new ProducerRecord<>("tasks", taskDTO);

        try {
            // Отправляем сообщение и ждём ответа
            RequestReplyFuture<String, Object, Object> future = replyingKafkaTemplate.sendAndReceive(record, Duration.ofSeconds(30));
            ConsumerRecord<String, Object> response = future.get(); // Ждём до 10 секунд

            if (response.value() == null) {
                throw new RuntimeException("Ошибка при создании задания");
            }

            return objectMapper.readValue(response.value().toString(), TaskCreateResponseDto.class); // Ответ от Consumer
        } catch (Exception e) {
            throw new RuntimeException("Failed to send task with reply", e);
        }
    }
}
