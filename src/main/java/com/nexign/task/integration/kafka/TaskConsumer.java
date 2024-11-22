package com.nexign.task.integration.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexign.task.model.dto.TaskCreateRequestDto;
import com.nexign.task.model.dto.TaskCreateResponseDto;
import com.nexign.task.model.entity.Task;
import com.nexign.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskConsumer {

    private final TaskService taskService;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "tasks", groupId = "task-group", autoStartup = "true")
    public void consumeTask(ConsumerRecord<String, Object> record) {
        Header header = record.headers().lastHeader("task_id");
        String correlationId = new String(header.value());

        try {
            TaskCreateRequestDto taskDTO = objectMapper.readValue(record.value().toString(), TaskCreateRequestDto.class);
            var task = taskService.create(modelMapper.map(taskDTO, Task.class));

            ProducerRecord<String, Object> response = new ProducerRecord<>("task-replies", correlationId, modelMapper.map(task, TaskCreateResponseDto.class));
            response.headers().add(header);

            // Отправляем успешный ответ
            kafkaTemplate.send(response);
        } catch (Exception e) {
            // Обрабатываем ошибку
            kafkaTemplate.send("task-replies", correlationId, null);
        }
    }
}
