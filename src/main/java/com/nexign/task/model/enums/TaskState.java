package com.nexign.task.model.enums;

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
    FINISHED("Завершено");

    private final String value;
}
