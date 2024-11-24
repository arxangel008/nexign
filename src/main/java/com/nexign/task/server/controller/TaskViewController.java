package com.nexign.task.server.controller;

import com.nexign.task.common.model.BaseErrorDto;
import com.nexign.task.server.model.dto.TaskViewResponseDto;
import com.nexign.task.server.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/task")
@Tag(name = "TaskSend", description = "Контроллер для работы с заданием")
public class TaskViewController {

    private final TaskService taskService;
    private final ModelMapper modelMapper;

    @GetMapping("/{id}")
    @Operation(description = "Получение задания", responses = {
            @ApiResponse(responseCode = "201", description = "Создано успешно"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена", content = @Content(schema = @Schema(implementation = BaseErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Остальные ошибки", content = @Content(schema = @Schema(implementation = BaseErrorDto.class))),
    })
    public ResponseEntity<TaskViewResponseDto> getTask(@Parameter(description = "Идентификатор задания") @PathVariable("id") Long id) {
        var task = taskService.getById(id);
        return ok(modelMapper.map(task, TaskViewResponseDto.class));
    }
}
