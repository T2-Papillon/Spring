package com.boogle.papplan.repository;

import com.boogle.papplan.entity.ProjectPriority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectPriorityRepository extends JpaRepository<ProjectPriority, String> {
    Optional<ProjectPriority> projectPriorityId(String projectPriority);
}
