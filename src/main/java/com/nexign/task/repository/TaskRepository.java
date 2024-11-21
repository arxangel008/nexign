package com.nexign.task.repository;

import com.nexign.task.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий по работе с заданиями
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
