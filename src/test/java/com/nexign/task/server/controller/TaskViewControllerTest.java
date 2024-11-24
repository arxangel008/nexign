package com.nexign.task.server.controller;

import com.nexign.task.config.AbstractIntegrationTestConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тесты контроллера для просмотра заданий")
@Sql("/script/task_test.sql")
class TaskViewControllerTest extends AbstractIntegrationTestConfiguration {

    @Test
    @DisplayName("Получение задания")
    void getTask() throws Exception {
        mockMvc.perform(get("/api/v1/task/{id}", -1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(-1)))
                .andExpect(jsonPath("$.name", is("1")))
                .andExpect(jsonPath("$.executionTime", is(100)))
                .andExpect(jsonPath("$.state", is("COMPLETED")));
    }

    @Test
    @DisplayName("Получение задания: сущность не найдена")
    void getTaskNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/task/{id}", -100))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Задача с id = -100 не найдена")))
                .andExpect(jsonPath("$.path", is("/api/v1/task/-100")))
                .andExpect(jsonPath("$.exception", is("com.nexign.task.common.exception.NotFoundException")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }
}
