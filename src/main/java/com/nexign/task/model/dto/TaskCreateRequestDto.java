package com.nexign.task.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель запроса на создание задачи")
@Validated
public class TaskCreateRequestDto {

    @NotBlank(message = "должно быть заполнено")
    @Schema(description = "Наименование")
    private String name;

    @NotNull
    @Min(1)
    @Max(1000)
    @Schema(description = "Время выполнения")
    private long executionTime;
}
