package com.nexign.task.server.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель просмотра задания")
public class TaskViewResponseDto {

    @Schema(description = "Идентификатор")
    private Long id;

    @Schema(description = "Наименование")
    private String name;

    @Schema(description = "Время выполнения")
    private long executionTime;

    @Schema(description = "Статус задания")
    private String state;
}
