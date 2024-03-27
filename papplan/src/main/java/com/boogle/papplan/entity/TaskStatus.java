package com.boogle.papplan.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class TaskStatus {

    @Id
    @Column(length = 20, name = "task_status_id", nullable = false)
    private String taskStatusId;

    @Column(length = 20, name = "task_status_name", nullable = false)
    private String taskStatusName;

    @OneToMany(mappedBy = "taskStatus", cascade = CascadeType.REMOVE)
    private List<Task> tasks = new ArrayList<>();
}

/*
enum TaskStatus {
    TODO("진행예정"),
    DOING("진행중"),
    DONE("완료"),
    HOLD("보류"),
    TEST("테스트");

    private final String value;

    TaskStatus(String value) {
        this.value = value;
    }
}*/
