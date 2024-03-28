package com.boogle.papplan.repository;

import com.boogle.papplan.entity.TaskPriority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskPriorityRepository extends JpaRepository<TaskPriority, Integer> {
    Optional<TaskPriority> findByTaskPriorityName(String taskPriorityName);
}
