package com.nexign.task.common.config;

import com.nexign.task.common.exception.NotFoundException;
import com.nexign.task.common.model.BaseErrorDto;
import com.nexign.task.common.service.WebExceptionService;
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
    private final WebExceptionService webExceptionService;

    /**
     * Любые не обработанные ошибки
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseErrorDto> handleException(Exception exception, HttpServletRequest request) {
        return webExceptionService.makeResponse(INTERNAL_SERVER_ERROR, BaseErrorDto::new, exception, request);
    }

    /**
     * Ошибки отсутствия сущности
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseErrorDto> handleNotFoundException(NotFoundException exception, HttpServletRequest request) {
        return webExceptionService.makeResponse(NOT_FOUND, BaseErrorDto::new, exception, request);
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

        return webExceptionService.makeResponse(BAD_REQUEST, BaseErrorDto::new, exception, request, baseErrorDto -> baseErrorDto.setMessage(message));
    }
}
