package com.nexign.task.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * Базовая модель сообщения об ошибке
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
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
     * Заголовок ошибки
     */
    private String header;

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
     * Вид обработки сообщения на ui
     */
    private String type;

    /**
     * Время когда произошла ошибка
     */
    private LocalDateTime timestamp;
}
