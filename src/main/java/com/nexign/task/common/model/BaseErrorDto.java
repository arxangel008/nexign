package com.nexign.task.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Базовая модель сообщения об ошибке
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@JsonInclude(NON_NULL)
public class BaseErrorDto {

    /**
     * HTTP статус
     */
    private int status;

    /**
     * Описание HTTP статуса
     */
    private String error;

    /**
     * Сообщение ошибки
     */
    private String message;

    /**
     * Путь запроса
     */
    private String path;

    /**
     * Класс исключения
     */
    private String exception;

    /**
     * Stacktrace
     */
    private String trace;

    /**
     * Время когда произошла ошибка
     */
    private LocalDateTime timestamp;
}
