package com.boogle.papplan.repository;

import com.boogle.papplan.dto.StatisticProjectDto;
import com.boogle.papplan.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticRepository extends JpaRepository<Project, Integer> {

    // [통계분석] 특정 프로젝트의 프로젝트 제목, 기간(시작일과 종료일),
    // 참여자, 진행률, 작성일을 조회하는 메서드
    // GROUP_CONCAT은 JPQL에서는 지원되지 않아서, native query를 명시적으로 사용해야 한다
    // 그리고 조회결과가 해당 project전체필드가 아니므로, DTO를 생성해야 한다.
    @Query(value = "SELECT " +
            "p.proj_no AS projNo, " +
            "p.proj_title AS title, " +
            "p.proj_start_date AS startDate, " +
            "p.proj_end_date AS endDate, " +
            "GROUP_CONCAT(e.name SEPARATOR ', ') AS participants, " +
            "p.proj_percent AS progress, " +
            "p.proj_create_date AS createDate " +
            "FROM project p " +
            "LEFT JOIN contributor c ON p.proj_no = c.proj_no " +
            "LEFT JOIN employees e ON c.eno = e.eno " +
            "WHERE p.proj_no = :projNo " +
            "GROUP BY p.proj_no", nativeQuery = true)
    StatisticProjectDto findProjectDetailsByProjNo(Integer projNo);

}
