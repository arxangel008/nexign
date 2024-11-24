package com.nexign.task.server.repository;

import com.nexign.task.config.AbstractIntegrationTestConfiguration;
import com.nexign.task.server.model.entity.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static com.nexign.task.server.model.enums.TaskState.CREATED;
import static com.nexign.task.server.model.enums.TaskState.PROCESSING;
import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты репозитория заданий")
@Sql("/script/task_test.sql")
class TaskRepositoryTest extends AbstractIntegrationTestConfiguration {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    @DisplayName("Сохранение задания")
    void create() {
        var task = Task.builder()
                .name("test")
                .createDate(now())
                .state(CREATED)
                .executionTime(1000L)
                .build();

        var result = taskRepository.save(task);

        assertNotNull(result);
        assertNotNull(task.getId());
    }

    @Test
    @DisplayName("Получение для обработки")
    void getAllByPartition() {
        var tasks = taskRepository.getAllByPartition(10L);

        assertNotNull(tasks);
        assertEquals(3, tasks.size());

        tasks.forEach(task -> {
            assertEquals(PROCESSING, task.getState());
            assertNotNull(task.getProcessingDate());
            assertNull(task.getEndDate());
        });
    }
}
