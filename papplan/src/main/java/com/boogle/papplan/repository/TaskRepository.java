package com.boogle.papplan.repository;

import com.boogle.papplan.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByProjectProjNo(Integer projNo);

    Task findByProjectProjNoAndTaskNo(Integer projNo, Integer taskNo);

    Optional<Task> findByTaskNoAndProjectProjNo(Integer taskNo, Integer projNo);
}