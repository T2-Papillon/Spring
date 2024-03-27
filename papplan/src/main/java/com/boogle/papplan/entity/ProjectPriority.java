package com.boogle.papplan.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "ProjectPriority")
@Getter
public class ProjectPriority {

    @Id
    @Column(length = 20, name = "project_priority_id", nullable = false)
    private String projectPriorityId;

    @Column(length = 20, name = "project_priority_name", nullable = false)
    private String projectPriorityName;

    @OneToMany(mappedBy = "projectPriority", cascade = CascadeType.REMOVE)
    private List<Project> projects = new ArrayList<>();
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
