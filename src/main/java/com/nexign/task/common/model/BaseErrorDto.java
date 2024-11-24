package com.nexign.task.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@NoArgsConstructor
@JsonInclude(NON_NULL)
@Accessors(chain = true)
@Schema(description = "Базовая модель сообщения об ошибке")
public class BaseErrorDto {

    @Schema(description = "HTTP статус")
    private int status;

    @Schema(description = "Описание HTTP статуса")
    private String error;

    @Schema(description = "Сообщение ошибки")
    private String message;

    @Schema(description = "Путь запроса")
    private String path;

    @Schema(description = "Класс исключения")
    private String exception;

    @Schema(description = "Stacktrace")
    private String trace;

    @Schema(description = "Время когда произошла ошибка")
    private LocalDateTime timestamp;
}
