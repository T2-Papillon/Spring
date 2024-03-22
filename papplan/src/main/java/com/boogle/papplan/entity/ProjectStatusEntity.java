package com.boogle.papplan.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity(name = "ProjectStatus")
@Getter
public class ProjectStatusEntity {

    @Id
    @Column(length = 20, name = "project_status_id", nullable = false)
    private String project_status_id;

    @Column(length = 20, name = "project_status_name", nullable = false)
    private String project_status_name;
}

/*
enum ProjectStatus {
    TODO("진행예정"),
    GOING("진행중"),
    DONE("완료"),
    HOLD("보류");

    private final String value;

    ProjectStatus(String value) {
        this.value = value;
    }
}*/
