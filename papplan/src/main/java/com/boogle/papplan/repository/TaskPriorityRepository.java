package com.boogle.papplan.repository;

import com.boogle.papplan.entity.TaskPriority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskPriorityRepository extends JpaRepository<TaskPriority, Integer> {
}
