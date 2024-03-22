package com.boogle.papplan.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity(name = "ProjectPriority")
@Getter
public class ProjectPriority {

    @Id
    @Column(length = 20, name = "project_priority_id", nullable = false)
    private String project_priority_id;

    @Column(length = 20, name = "project_priority_name", nullable = false)
    private String project_priority_name;
}


/*
enum ProjectPriority {

    TODO("긴급"),
    GOING("높음"),
    DONE("보통"),
    HOLD("낮음");

    private final String value;

    ProjectPriority(String value) {
        this.value = value;
    }
}*/
