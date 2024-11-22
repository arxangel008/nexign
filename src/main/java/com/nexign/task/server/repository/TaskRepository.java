package com.nexign.task.server.repository;

import com.nexign.task.server.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий по работе с заданиями
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Получение задач.
     * Полученные задачи сперва блокируются для изменения сторонних инстансов.
     * Обновляется статус на "PROCESSING".
     *
     * @param limit ограничение задач
     * @return обновленные задачи
     */
    @Query(nativeQuery = true, value = """
            UPDATE task
            SET state = 'PROCESSING',
                processing_date = now()
            WHERE id IN (SELECT id 
                         FROM task 
                         WHERE state = 'CREATED'
                         ORDER BY id DESC
                         LIMIT :limit
                         FOR UPDATE SKIP LOCKED
                        )
            RETURNING id, name, state, execution_time, create_date, processing_date, end_date, error_description
            """)
    @Modifying
    List<Task> getAllByPartition(@Param("limit") Long limit);
}
