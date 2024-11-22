package com.nexign.task.common.exception;

import org.springframework.core.NestedRuntimeException;

/**
 * Исключение в случае отсутствия сущности
 */
public class NotFoundException extends NestedRuntimeException {
    public NotFoundException(String msg) {
        super(msg);
    }

    public NotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
