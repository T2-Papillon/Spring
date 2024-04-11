package com.boogle.papplan.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "ProjectStatus")
@Data
public class ProjectStatus {

    @Id
    @Column(length = 20, name = "project_status_id", nullable = false)
    private String projectStatusId;

    @Column(length = 20, name = "project_status_name", nullable = false)
    private String projectStatusName;

    @OneToMany(mappedBy = "projectStatus", cascade = CascadeType.REMOVE)
    private List<Project> projects = new ArrayList<>();
}

