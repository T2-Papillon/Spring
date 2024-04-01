package com.boogle.papplan.repository;

import com.boogle.papplan.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByProjectProjNo(Integer projNo);

    // 사원의 진행중인 업무 조회
    @Query("SELECT t FROM Task t WHERE " +
            " t.assignee LIKE (SELECT e.name FROM Employees e WHERE e.eno = (:empNo)) " +
            " AND t.taskStatus.taskStatusId = \"DOING\"")
    List<Task> findAllByEmpNoInProgress(Integer empNo);

    Task findByProjectProjNoAndTaskNo(Integer projNo, Integer taskNo);

}