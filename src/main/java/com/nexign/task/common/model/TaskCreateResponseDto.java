package com.nexign.task.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель ответа по созданию задачи")
public class TaskCreateResponseDto {

    @Schema(description = "Идентификатор", requiredMode = REQUIRED)
    private Long id;

    @Schema(description = "Наименование", requiredMode = REQUIRED)
    private String name;

    @Schema(description = "Время выполнения", requiredMode = REQUIRED)
    private long executionTime;

    @Schema(description = "Статус задания", requiredMode = REQUIRED)
    private String state;

    @Schema(description = "Сообщение при ошибке", requiredMode = REQUIRED)
    private String errorMessage;
}
