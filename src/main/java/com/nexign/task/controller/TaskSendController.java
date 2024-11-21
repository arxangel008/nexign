package com.nexign.task.controller;

import com.nexign.task.model.dto.TaskCreateRequestDto;
import com.nexign.task.model.dto.TaskCreateResponseDto;
import com.nexign.task.model.entity.Task;
import com.nexign.task.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;


@Validated
@RestController
@RequestMapping(value = "/api/v1/task/send")
@RequiredArgsConstructor
@Tag(name = "TaskSend", description = "Контроллер для отправки задания")
public class TaskSendController {

    private final TaskService taskService;
    private final ModelMapper modelMapper;

    @Operation(description = "Отправка задания в очередь")
    @PostMapping("/to-queue")
    public ResponseEntity<TaskCreateResponseDto> createTask(@Parameter(description = "Информация по заданию") @Valid @RequestBody TaskCreateRequestDto taskCreateRequestDto) {
        var task = taskService.create(modelMapper.map(taskCreateRequestDto, Task.class));
        return ok(modelMapper.map(task, TaskCreateResponseDto.class));
    }
}
