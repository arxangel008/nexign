package com.nexign.task.server.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Статус выполнения задания
 */
@Getter
@AllArgsConstructor
public enum TaskState {

    CREATED("Создано"),
    PROCESSING("В процессе"),
    ERROR("Ошибка"),
    COMPLETED("Завершено");

    private final String value;
}
