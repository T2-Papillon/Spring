package com.boogle.papplan.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity(name = "TaskPriority")
@Getter
public class TaskPriority {

    @Id
    @Column(length = 20, name = "task_priority_id", nullable = false)
    private String taskPriorityId;

    @Column(length = 20, name = "task_priority_name", nullable = false)
    private String taskPriorityName;
}

