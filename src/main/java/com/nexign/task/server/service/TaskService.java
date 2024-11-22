package com.nexign.task.server.service;

import com.nexign.task.common.exception.NotFoundException;
import com.nexign.task.server.model.entity.Task;
import com.nexign.task.server.repository.TaskRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.nexign.task.server.model.enums.TaskState.*;
import static java.lang.String.format;
import static java.time.LocalDateTime.now;

/**
 * Сервис задания
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;

    /**
     * Создание задачи
     *
     * @param task задача
     * @return созданная задача
     */
    @Transactional
    public Task create(@NonNull Task task) {
        task.setCreateDate(now());
        task.setState(CREATED);
        return repository.save(task);
    }

    /**
     * Получение задачи
     *
     * @param id идентификатор задачи
     * @return задача
     */
    @Transactional(readOnly = true)
    public Task getById(@NonNull Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("Задача с id = %s не найдена", id)));
    }

    /**
     * Получение заданий по условиям партиции
     *
     * @param partition кол-во данных
     * @return задачи
     */
    @Transactional
    public List<Task> getAllByPartition(@NonNull Long partition) {
        return repository.getAllByPartition(partition);
    }

    /**
     * Проставления признака завершения задачи
     *
     * @param task задача
     */
    @Transactional
    public void complete(@NonNull Task task) {
        task.setState(COMPLETED);
        task.setEndDate(now());

        repository.save(task);
    }

    /**
     * Проставления признака ошибка выполнения
     *
     * @param task задача
     */
    @Transactional
    public void error(@NonNull Task task, String errorMessage) {
        task.setState(ERROR);
        task.setEndDate(now());
        task.setErrorDescription(errorMessage);

        repository.save(task);
    }
}
