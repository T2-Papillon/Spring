package com.boogle.papplan.repository;

import com.boogle.papplan.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskStatusRepository extends JpaRepository<TaskStatus, Integer> {

}
