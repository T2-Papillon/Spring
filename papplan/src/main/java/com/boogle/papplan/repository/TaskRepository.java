package com.boogle.papplan.repository;

import com.boogle.papplan.entity.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query("SELECT DISTINCT t FROM Task t " +
            "JOIN FETCH t.project " +
            "JOIN FETCH t.assignee " +
            "WHERE t.project.projNo = :projNo")
    List<Task> findAllByProjectProjNo(Integer projNo);

    // 사원에게 부여된 모든 업무 조회
    @Query("SELECT DISTINCT t FROM Task t " +
            "JOIN FETCH t.project " +
            "JOIN FETCH t.assignee " +
            "WHERE t.assignee.eno = :empNo")
    List<Task> findAllByEmpNo(Integer empNo);

    @Query("SELECT DISTINCT t FROM Task t " +
            "JOIN FETCH t.project " +
            "JOIN FETCH t.assignee " +
            "WHERE t.taskNo = (:taskNo) " +
            " AND t.project.projNo = (:projNo) ")
    Task findByProjectProjNoAndTaskNo(Integer projNo, Integer taskNo);

    // 업무명 또는 담당자 이름으로 Task 검색
    @Query("SELECT t FROM Task t " +
            "JOIN FETCH t.project " +
            "JOIN FETCH t.assignee " +
            " WHERE t.project.projNo = :projNo AND " +
            " (LOWER(t.taskTitle) LIKE LOWER(:term) OR " +
            "LOWER(t.assignee.name) LIKE LOWER(:term))")
    List<Task> findByProjectIdAndTaskTitleOrAssignee(Integer projNo, String term, Pageable pageable);

    // 업무 상태로 업무를 조회하는 메서드
    @Query("SELECT t FROM Task t " +
            "JOIN FETCH t.project " +
            "JOIN FETCH t.assignee " +
            "JOIN FETCH t.taskStatus " +
            "WHERE t.project.projNo = :projNo " +
            "AND t.taskStatus.taskStatusId = :taskStatusId")
    List<Task> findByProjectProjNoAndTaskStatusTaskStatusId(Integer projNo, String taskStatusId);
}
