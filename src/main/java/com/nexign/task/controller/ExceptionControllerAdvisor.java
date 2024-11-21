package com.nexign.task.controller;

import com.nexign.task.model.dto.BaseErrorDto;
import com.nexign.task.model.exception.NotFoundException;
import com.nexign.task.service.WebExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpStatus.*;

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

    /**
     * Ошибки отсутствия сущности
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseErrorDto> handleNotFoundException(NotFoundException exception, HttpServletRequest request) {
        return handler.makeResponse(NOT_FOUND, BaseErrorDto::new, exception, request);
    }

    /**
     * Ошибки валидации
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseErrorDto> handleValidateException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        var message = exception.getAllErrors().stream()
                .map(e -> (FieldError) e)
                .map(e -> "Поле: " + e.getField() + " " + e.getDefaultMessage())
                .collect(joining("; "));

        return handler.makeResponse(BAD_REQUEST, BaseErrorDto::new, exception, request, baseErrorDto -> baseErrorDto.setMessage(message));
    }
}
