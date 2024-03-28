package com.boogle.papplan.repository;

import com.boogle.papplan.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Integer> {
    Optional<TaskStatus> findByTaskStatusName(String taskStatusName);
}
