package com.boogle.papplan.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "ProjectStatus")
@Data
public class ProjectStatus {

    @Id
    @Column(length = 20, name = "project_status_id", nullable = false)
    private String projectStatusId;

    @Column(length = 20, name = "project_status_name", nullable = false)
    private String projectStatusName;
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
