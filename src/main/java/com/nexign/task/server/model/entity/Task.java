package com.nexign.task.server.model.entity;

import com.nexign.task.server.model.enums.TaskState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * Задача
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task")
public class Task {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    /**
     * Наименование
     */
    @Column(name = "name")
    private String name;

    /**
     * Статус
     */
    @Enumerated(STRING)
    @Column(name = "state")
    private TaskState state;

    /**
     * Время выполнения
     */
    @Column(name = "execution_time")
    private long executionTime;

    /**
     * Дата и время создания
     */
    @Column(name = "create_date")
    private LocalDateTime createDate;

    /**
     * Дата и время начала обработки
     */
    @Column(name = "processing_date")
    private LocalDateTime processingDate;

    /**
     * Время окончания
     */
    @Column(name = "end_date")
    private LocalDateTime endDate;

    /**
     * Описание ошибки
     */
    @Column(name = "error_description")
    private String errorDescription;

}
