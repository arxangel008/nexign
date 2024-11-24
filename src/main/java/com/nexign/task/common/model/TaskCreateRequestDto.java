package com.nexign.task.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@Validated
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель запроса на создание задачи")
public class TaskCreateRequestDto {

    @NotBlank(message = "должно быть заполнено")
    @Schema(description = "Наименование", requiredMode = REQUIRED)
    private String name;

    @Min(1)
    @Max(1000)
    @Schema(description = "Время выполнения", requiredMode = REQUIRED)
    private long executionTime;
}
