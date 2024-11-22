package com.nexign.task.server.service;

import com.nexign.task.common.model.TaskCreateRequestDto;
import com.nexign.task.common.model.TaskCreateResponseDto;
import com.nexign.task.server.model.entity.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskConsumer {

    private final TaskService taskService;
    private final ModelMapper modelMapper;

    @KafkaListener(topics = "tasks", groupId = "task-group", autoStartup = "true", containerFactory = "requestListenerContainerFactory")
    @SendTo
    public TaskCreateResponseDto consumeTask(TaskCreateRequestDto record) {
        try {
            var task = taskService.create(modelMapper.map(record, Task.class));
            return modelMapper.map(task, TaskCreateResponseDto.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);

            var result = modelMapper.map(record, TaskCreateResponseDto.class);
            result.setErrorMessage(e.getMessage());

            return result;
        }
    }
}
