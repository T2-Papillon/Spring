package com.boogle.papplan.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "TaskPriority")
@Getter
public class TaskPriority {

    @Id
    @Column(length = 20, name = "task_priority_id", nullable = false)
    private String task_priority_id;

    @Column(length = 20, name = "task_priority_name", nullable = false)
    private String task_priority_name;

    @OneToMany(mappedBy = "taskPriority", cascade = CascadeType.REMOVE)
    private List<Task> tasks = new ArrayList<>();
}

