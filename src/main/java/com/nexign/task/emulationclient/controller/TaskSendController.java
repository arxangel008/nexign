package com.nexign.task.emulationclient.controller;

import com.nexign.task.common.model.BaseErrorDto;
import com.nexign.task.common.model.TaskCreateRequestDto;
import com.nexign.task.common.model.TaskCreateResponseDto;
import com.nexign.task.emulationclient.service.TaskKafkaProducer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/task/send")
@Tag(name = "TaskSend", description = "Контроллер для отправки задания")
public class TaskSendController {

    private final TaskKafkaProducer taskKafkaProducer;

    @PostMapping("/to-queue")
    @Operation(description = "Отправка задания в очередь", responses = {
            @ApiResponse(responseCode = "201", description = "Создано успешно"),
            @ApiResponse(responseCode = "400", description = "Ошибки валидации", content = @Content(schema = @Schema(implementation = BaseErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Остальные ошибки", content = @Content(schema = @Schema(implementation = BaseErrorDto.class))),
    })
    public ResponseEntity<TaskCreateResponseDto> createTask(@Parameter(description = "Информация по заданию") @Valid @RequestBody TaskCreateRequestDto taskCreateRequestDto) {
        return status(CREATED).body(taskKafkaProducer.sendTaskWithReply(taskCreateRequestDto));
    }
}
