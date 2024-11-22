package com.nexign.task.common.service;

import com.nexign.task.common.model.BaseErrorDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.lang.Boolean.parseBoolean;
import static java.util.Objects.nonNull;
import static org.springframework.boot.autoconfigure.web.ErrorProperties.IncludeAttribute.ALWAYS;
import static org.springframework.boot.autoconfigure.web.ErrorProperties.IncludeAttribute.ON_PARAM;
import static org.springframework.http.ResponseEntity.status;

/**
 * Обработчик ошибок приложения для формирования HTTP ответов
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebExceptionHandler {

    /**
     * Резервный предохранитель если stacktrace окажется слишком большим
     */
    private static final int MAX_TRACE_SIZE = 25_000;

    private final ServerProperties serverProperties;

    /**
     * Обработать исключительную ситуацию
     *
     * @param status                статус ошибки
     * @param errorNoArgConstructor пустой dto для отображения указанной ошибки
     * @param exception             исключение
     * @param request               оригинальный запрос
     * @param <D>                   тип контейнера для ошибки производного от BaseErrorDto
     * @return web ответ содержащий информацию об ошибке
     */
    public <D extends BaseErrorDto> ResponseEntity<D> makeResponse(@NonNull HttpStatus status,
                                                                   @NonNull Supplier<D> errorNoArgConstructor,
                                                                   @NonNull Exception exception,
                                                                   @NonNull HttpServletRequest request) {

        return makeResponse(status, errorNoArgConstructor, exception, request, null);
    }

    /**
     * Обработать исключительную ситуацию
     *
     * @param status                статус ошибки
     * @param errorNoArgConstructor пустой dto для отображения указанной ошибки
     * @param exception             исключение
     * @param request               оригинальный запрос
     * @param enrich                лямбда функция позволяющая кастомизировать
     *                              базовый ответ добавляя новую или удаляя существующую информацию
     * @param <D>                   тип контейнера для ошибки производного от BaseErrorDto
     * @return web ответ содержащий информацию об ошибке
     */
    public <D extends BaseErrorDto> ResponseEntity<D> makeResponse(@NonNull HttpStatus status,
                                                                   @NonNull Supplier<D> errorNoArgConstructor,
                                                                   @NonNull Exception exception,
                                                                   @NonNull HttpServletRequest request,
                                                                   Consumer<D> enrich) {
        log.error(exception.getMessage(), exception);

        D errorDto = prepareBaseErrorModel(errorNoArgConstructor, exception, request, status);

        if (nonNull(enrich)) {
            enrich.accept(errorDto);
        }

        return status(status).body(errorDto);
    }

    /**
     * Подготовка Dto
     */
    private <D extends BaseErrorDto> D prepareBaseErrorModel(Supplier<D> errorNoArgConstructor,
                                                             Exception exception,
                                                             HttpServletRequest request,
                                                             HttpStatus status) {
        D errorDto = errorNoArgConstructor.get();

        errorDto.setStatus(status.value())
                .setError(status.getReasonPhrase())
                .setMessage(exception.getMessage())
                .setPath(request.getRequestURI())
                .setException(exception.getClass().getName())
                .setTimestamp(LocalDateTime.now());

        if (needsToIncludeStacktrace(parseBoolean(request.getParameter("trace")))) {
            errorDto.setTrace(prepareStacktrace(exception));
        }

        return errorDto;
    }

    /**
     * Подготовка stacktrace
     */
    private String prepareStacktrace(Exception e) {
        String stackTraceText = ExceptionUtils.getStackTrace(e);
        return stackTraceText.length() < MAX_TRACE_SIZE
                ? stackTraceText
                : stackTraceText.substring(0, MAX_TRACE_SIZE);
    }

    /**
     * Нужно ли добавить stacktrace к отображению ошибки
     */
    private boolean needsToIncludeStacktrace(boolean traced) {
        ErrorProperties.IncludeAttribute type = serverProperties
                .getError()
                .getIncludeStacktrace();

        return type == ALWAYS || (type == ON_PARAM && traced);
    }
}
