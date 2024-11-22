package com.nexign.task.server.service;

import com.nexign.task.common.exception.NotFoundException;
import com.nexign.task.server.model.entity.Task;
import com.nexign.task.server.repository.TaskRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.nexign.task.server.model.enums.TaskState.CREATED;
import static java.lang.String.format;

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
        task.setCreateDate(LocalDateTime.now());
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
}
