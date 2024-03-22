package com.boogle.papplan.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity(name = "TaskPriority")
@Getter
public class TaskPriorityEntity {

    @Id
    @Column(length = 20, name = "task_priority_id", nullable = false)
    private String task_priority_id;

    @Column(length = 20, name = "task_priority_name", nullable = false)
    private String task_priority_name;

}

/*
enum TaskPriority{
    LV0("긴급"),
    LV1("높음"),
    LV2("보통"),
    LV3("낮음");

    private final String value;

    TaskPriority(String value) {
        this.value = value;
    }

}*/
