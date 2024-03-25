package com.boogle.papplan.repository;

import com.boogle.papplan.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Project, Integer> {

    // 프로젝트명, PM, 참여자 이름으로 프로젝트 검색
    @Query("SELECT p FROM Project p WHERE p.projTitle LIKE %:keyword% OR p.projPm LIKE %:keyword% OR p.contributor.name LIKE %:keyword%")
    List<Project> findByKeyword(@Param("keyword") String keyword);

    // 상태에 따라 프로젝트 필터링
    // 상태 ID 리스트를 받아 해당 상태의 프로젝트를 필터링하는 메서드 추가
    List<Project> findByProjectStatusIdIn(List<String> statusIds);

//    // 최신순으로 프로젝트 정렬
//    List<Project> findByOrderByCreateDateDesc();
//
//    // 우선순위순으로 프로젝트 정렬
//    List<Project> findByOrderByPriorityLevelDesc();

}
