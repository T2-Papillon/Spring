package com.boogle.papplan.repository;

import com.boogle.papplan.entity.Task;
import org.springframework.data.domain.Pageable;
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

    // 사원에게 부여된 모든 업무 조회
    @Query("SELECT t FROM Task t WHERE " +
            " t.assignee LIKE (SELECT e.name FROM Employees e WHERE e.eno = (:empNo)) ")
    List<Task> findAllByEmpNo(Integer empNo);

    Task findByProjectProjNoAndTaskNo(Integer projNo, Integer taskNo);

    // 업무명 또는 담당자 이름으로 Task 검색
    @Query("SELECT t FROM Task t WHERE t.project.projNo = :projNo AND (" +
            "LOWER(t.taskTitle) LIKE LOWER(:term) OR " +
            "LOWER(t.assignee) LIKE LOWER(:term))")
    List<Task> findByProjectIdAndTaskTitleOrAssignee(Integer projNo, String term, Pageable pageable);

    // 업무 상태로 업무를 조회하는 메서드
    @Query("SELECT t FROM Task t WHERE t.project.projNo = :projNo AND t.taskStatus.taskStatusId = :taskStatusId")
    List<Task> findByProjectProjNoAndTaskStatusTaskStatusId(Integer projNo, String taskStatusId);
}
