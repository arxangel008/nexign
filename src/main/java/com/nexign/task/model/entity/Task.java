package com.nexign.task.model.entity;

import com.nexign.task.model.enums.TaskState;
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
     * Статус
     */
    @Column(name = "state")
    @Enumerated(STRING)
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