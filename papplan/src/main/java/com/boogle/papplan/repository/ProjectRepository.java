package com.boogle.papplan.repository;

import com.boogle.papplan.entity.Project;
import com.boogle.papplan.entity.ProjectStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    // PM으로 참여한 프로젝트를 조회하는 메서드
    List<Project> findByProjPm(String projPm);

    // 프로젝트에 참여자로서 포함된 프로젝트를 조회하는 메서드
    @Query("SELECT c.project FROM Contributor c WHERE c.employees.id = :id")
    List<Project> findProjectsByContributorId(@Param("id") Long id);

    // 프로젝트 상태로 프로젝트를 조회하는 메서드
    List<Project> findByProjectStatus_ProjectStatusId(String projectStatusId);

    // 프로젝트명 또는 PM 또는 참여자로 프로젝트 검색
    @Query("SELECT p FROM Project p WHERE " +
            "LOWER(p.projTitle) LIKE LOWER(:searchTerm) OR " +
            "LOWER(p.projPm) LIKE LOWER(:searchTerm) OR " +
            "p.id IN (SELECT c.project.id FROM Contributor c WHERE LOWER(c.employees.name) LIKE LOWER(:searchTerm))")
    List<Project> findByTitleOrPmOrContributor(String searchTerm);

    // 프로젝트 번호(projNo)에 해당하는 프로젝트를 조회하는 메서드
    Project findByProjNo(Integer projNo);
}
