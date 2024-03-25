package com.boogle.papplan.repository;

import com.boogle.papplan.entity.Project;
import com.boogle.papplan.entity.ProjectStatus;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    // PM으로 참여한 프로젝트를 조회하는 메서드
    List<Project> findByProjPm(String projPm);

    // 특정 상태의 프로젝트를 조회하는 메서드
    List<Project> findByProjectStatus(ProjectStatus projectStatus);

    // 프로젝트명 또는 PM 또는 참여자 이름으로 프로젝트 검색
    List<Project> findByProjTitleAndProjPm(String projTitle, String projPm);

    // 프로젝트 번호(projNo)에 해당하는 프로젝트를 조회하는 메서드
    Project findByProjNo(Integer projNo);
}
