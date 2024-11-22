package com.nexign.task.server.service;

import com.nexign.task.server.model.entity.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskProcessingService {

    private final TaskService taskService;

    /**
     * Обработка задачи
     */
    @Async
    public void processTask(Task task) {
        try {
            log.info("Обрабатывается задача: {} Поток: {}", task.getId(), currentThread().getName());

            sleep(task.getExecutionTime());

            taskService.complete(task);

            log.info("Задача завершена: {}", task.getId());
        } catch (Exception e) {
            log.error("Ошибка обработки задачи: {} {}", task.getId(), e.getMessage());
            taskService.error(task, e.getMessage());
        }
    }
}
