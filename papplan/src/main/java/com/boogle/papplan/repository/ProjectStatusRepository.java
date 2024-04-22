package com.boogle.papplan.repository;

import com.boogle.papplan.entity.Project;
import com.boogle.papplan.entity.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, String> {
    Optional<ProjectStatus> projectStatusId(String projectStatus);
}

