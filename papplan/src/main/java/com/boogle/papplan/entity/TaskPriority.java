package com.boogle.papplan.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "TaskPriority")
@Data
public class TaskPriority {

    @Id
    @Column(length = 20, name = "task_priority_id", nullable = false)
    private String taskPriorityId;

    @Column(length = 20, name = "task_priority_name", nullable = false)
    private String taskPriorityName;

    @OneToMany(mappedBy = "taskPriority", cascade = CascadeType.REMOVE)
    private List<Task> tasks = new ArrayList<>();


}

