package com.boogle.papplan.repository;

import com.boogle.papplan.interfaces.StatisticTaskStatusDto;
import com.boogle.papplan.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticRepository extends JpaRepository<Project, Integer> {

    // [프로젝트] 특정 프로젝트의 프로젝트 제목, 기간(시작일과 종료일),
    // 참여자, 진행률, 작성일을 조회하는 메서드
    // 기본 CRUD 연산 및 페이징, 정렬을 위한 메서드들을 자동으로 제공받음
    // Criteria API를 사용하는 복잡한 쿼리는 주로 서비스 클래스 내에서 처리


//     [프로젝트] 특정 프로젝트의 업무들에 대해 각 진행 상태별로 업무가 몇 개씩 있는지 조회
    @Query("SELECT " +
            "ts.task_status_name taskStatusName, COUNT(t.task_no) taskNo " +
            "FROM task t " +
            "JOIN task_status ts ON t.tasks_no = ts.task_status_id " +
            "WHERE t.proj_no = :projNo " +
            "GROUP BY ts.task_status_name")
    List<StatisticTaskStatusDto> findTaskCountByStatus(@Param("projNo") Integer projNo);
}
