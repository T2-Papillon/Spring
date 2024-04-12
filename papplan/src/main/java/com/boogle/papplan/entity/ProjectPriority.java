package com.boogle.papplan.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "ProjectPriority")
@Data
public class ProjectPriority {

    @Id
    @Column(length = 20, name = "project_priority_id", nullable = false)
    private String projectPriorityId;

    @Column(length = 20, name = "project_priority_name", nullable = false)
    private String projectPriorityName;

    @OneToMany(mappedBy = "projectPriority", cascade = CascadeType.REMOVE)
    private List<Project> projects = new ArrayList<>();
}


