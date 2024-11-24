package com.nexign.task.emulationclient.controller;

import com.nexign.task.common.model.BaseErrorDto;
import com.nexign.task.common.model.TaskCreateRequestDto;
import com.nexign.task.config.AbstractIntegrationTestConfiguration;
import com.nexign.task.utils.JsonUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.nexign.task.utils.JsonUtil.toJsonString;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тесты контроллера для отправки задания")
class TaskSendControllerTest extends AbstractIntegrationTestConfiguration {

    @Test
    @DisplayName("Отправка задания в очередь")
    void create() throws Exception {
        var request = TaskCreateRequestDto.builder()
                .name("test")
                .executionTime(500)
                .build();

        mockMvc.perform(post("/api/v1/task/send/to-queue")
                        .contentType(APPLICATION_JSON)
                        .content(toJsonString(request))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("test")))
                .andExpect(jsonPath("$.executionTime", is(500)))
                .andExpect(jsonPath("$.state", is("CREATED")));
    }

    @Test
    @DisplayName("Отправка задания в очередь: ошибка валидации")
    void createNotFound() throws Exception {
        var request = TaskCreateRequestDto.builder()
                .name("")
                .executionTime(0)
                .build();

        var messages = List.of("Поле: executionTime must be greater than or equal to 1", "Поле: name должно быть заполнено");

        var result = mockMvc.perform(post("/api/v1/task/send/to-queue")
                        .contentType(APPLICATION_JSON)
                        .content(toJsonString(request))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();


        var response = JsonUtil.toObject(result, BaseErrorDto.class);

        assertNotNull(response);
        assertEquals("/api/v1/task/send/to-queue", response.getPath());
        assertEquals("org.springframework.web.bind.MethodArgumentNotValidException", response.getException());
        assertNotNull(response.getTimestamp());
        assertTrue(asList(response.getMessage().split("; ")).containsAll(messages));
    }
}
