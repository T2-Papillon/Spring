package com.boogle.papplan.repository;

import com.boogle.papplan.entity.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, String> {
}

