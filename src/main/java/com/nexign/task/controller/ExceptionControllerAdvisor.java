package com.nexign.task.controller;

import com.nexign.task.model.dto.BaseErrorDto;
import com.nexign.task.service.WebExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Базовый обработчик нештатных ситуаций
 */
@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionControllerAdvisor {

    /**
     * Обработчик ошибок
     */
    private final WebExceptionHandler handler;

    /**
     * Любые не обработанные ошибки
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseErrorDto> handleException(Exception exception, HttpServletRequest request) {
        return handler.makeResponse(INTERNAL_SERVER_ERROR, BaseErrorDto::new, exception, request);
    }
}
