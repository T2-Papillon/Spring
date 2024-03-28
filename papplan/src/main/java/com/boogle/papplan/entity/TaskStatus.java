package com.boogle.papplan.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class TaskStatus {

    @Id
    @Column(length = 20, name = "task_status_id", nullable = false)
    private String taskStatusId;

    @Column(length = 20, name = "task_status_name", nullable = false)
    private String taskStatusName;

    @OneToMany(mappedBy = "taskStatus", cascade = CascadeType.REMOVE)
    private List<Task> tasks = new ArrayList<>();
}

