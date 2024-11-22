package com.nexign.task.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель ответа по созданию задачи")
public class TaskCreateResponseDto {

    @Schema(description = "Идентификатор")
    private Long id;

    @Schema(description = "Наименование")
    private String name;

    @Schema(description = "Время выполнения")
    private long executionTime;

    @Schema(description = "Статус задания")
    private String state;

    @Schema(description = "Сообщение при ошибке")
    private String errorMessage;
}
