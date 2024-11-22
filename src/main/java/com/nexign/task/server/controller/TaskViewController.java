package com.nexign.task.server.controller;

import com.nexign.task.common.model.TaskCreateResponseDto;
import com.nexign.task.server.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/task")
@RequiredArgsConstructor
@Tag(name = "TaskSend", description = "Контроллер для работы с заданием")
public class TaskViewController {

    private final TaskService taskService;
    private final ModelMapper modelMapper;

    @Operation(description = "Получение задания")
    @GetMapping("/{id}")
    public ResponseEntity<TaskCreateResponseDto> getTask(@Parameter(description = "Идентификатор задания") @PathVariable("id") Long id) {
        var task = taskService.getById(id);
        return ok(modelMapper.map(task, TaskCreateResponseDto.class));
    }
}
