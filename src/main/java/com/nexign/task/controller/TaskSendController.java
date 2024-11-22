package com.nexign.task.controller;

import com.nexign.task.integration.kafka.TaskKafkaProducer;
import com.nexign.task.model.dto.TaskCreateRequestDto;
import com.nexign.task.model.dto.TaskCreateResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.status;


@Validated
@RestController
@RequestMapping(value = "/api/v1/task/send")
@RequiredArgsConstructor
@Tag(name = "TaskSend", description = "Контроллер для отправки задания")
public class TaskSendController {

    private final TaskKafkaProducer taskKafkaProducer;

    @Operation(description = "Отправка задания в очередь")
    @PostMapping("/to-queue")
    public ResponseEntity<TaskCreateResponseDto> createTask(@Parameter(description = "Информация по заданию") @Valid @RequestBody TaskCreateRequestDto taskCreateRequestDto) {
        return status(CREATED).body(taskKafkaProducer.sendTaskWithReply(taskCreateRequestDto));
    }
}
