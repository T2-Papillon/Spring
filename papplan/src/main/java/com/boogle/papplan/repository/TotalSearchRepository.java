package com.boogle.papplan.repository;

import com.boogle.papplan.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TotalSearchRepository extends JpaRepository<Project, Integer> {

    // 프로젝트명, PM, 참여자 이름으로 프로젝트 검색
    List<Project> findByProjects(String projTitle, String projPm, String id);

    // 상태에 따라 프로젝트 필터링
    List<Project> findByProjectStatusId(String projectStatusId);

    // 최신순으로 프로젝트 정렬
    List<Project> findByOrderByCreateDateDesc();

    // 우선순위순으로 프로젝트 정렬
    List<Project> findByOrderByPriorityLevelDesc();

}
