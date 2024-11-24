package com.nexign.task.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.TimeZone.getTimeZone;
import static lombok.AccessLevel.PRIVATE;

/**
 * Служебный класс для преобразования Java-объектов в строковый формат
 */
@Slf4j
@NoArgsConstructor(access = PRIVATE)
public final class JsonUtil {

    private static final ObjectMapper jsonMapper = new ObjectMapper()
            .setTimeZone(getTimeZone(ZoneId.of("Europe/Moscow")))
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule())
            .disable(WRITE_DATES_AS_TIMESTAMPS);

    /**
     * Сериализация объекта в JSON-формат
     *
     * @param object Объект сериализации
     * @return JSON-представление объекта
     */
    public static String toJsonString(final Object object) {
        try {
            return jsonMapper.writeValueAsString(object);
        } catch (final JsonProcessingException ex) {
            throw new IllegalArgumentException("Ошибка при сериализации JSON", ex);
        }
    }

    /**
     * Десериализация
     *
     * @param jsonString JSON-представление объекта
     * @param valueType  желаемый класс
     * @param <T>        класс в который будет происходить преобразование
     * @return преобразованный объект
     */
    public static <T> T toObject(String jsonString, Class<T> valueType) {
        try {
            return jsonMapper.readValue(jsonString.getBytes(UTF_8), valueType);
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка преобразования JSON в объект", e);
        }
    }
}
