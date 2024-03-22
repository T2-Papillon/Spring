package com.boogle.papplan.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity(name = "TaskStatus")
@Getter
public class TaskStatusEntity {

    @Id
    @Column(length = 20, name = "task_status_id", nullable = false)
    private String task_status_id;

    @Column(length = 20, name = "task_status_name", nullable = false)
    private String task_status_name;
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
