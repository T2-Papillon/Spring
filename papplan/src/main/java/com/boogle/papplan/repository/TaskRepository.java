package com.boogle.papplan.repository;

import com.boogle.papplan.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByProjectProjNo(Integer projNo);

    @Query("SELECT t FROM Task t WHERE " +
            " t.assignee LIKE (SELECT e.name FROM Employees e WHERE e.eno = (:empNo))")
    List<Task> findAllByEmpNo(Integer empNo);

    Task findByProjectProjNoAndTaskNo(Integer projNo, Integer taskNo);
}