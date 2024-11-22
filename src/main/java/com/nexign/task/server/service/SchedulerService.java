package com.nexign.task.server.service;

import com.nexign.task.server.model.entity.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Планировщик заданий
 */
@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerService {

    /**
     * Ограничение на получение записей
     */
    @Value("${task.limit}")
    private Long limit;

    private final TaskService taskService;
    private final TaskProcessingService taskProcessingService;

    /**
     * Обработка задач
     */
    @Scheduled(cron = "${task.processing-cron}")
    public void scheduledTask() {
        var tasks = taskService.getAllByPartition(limit);

        for (Task task : tasks) {
            taskProcessingService.processTask(task);
        }
    }
}
