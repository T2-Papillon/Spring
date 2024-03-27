package com.boogle.papplan.repository;

import com.boogle.papplan.entity.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Project, Long> {
    // projectStatusId를 기반으로 프로젝트를 필터링
    List<Project> findByProjectStatus_ProjectStatusId(String projectStatusId);

    // 프로젝트명, PM, 참여자로 프로젝트 검색
    @Query("SELECT p FROM Project p WHERE " +
            "LOWER(p.projTitle) LIKE LOWER(:searchTerm) OR " +
            "LOWER(p.projPm) LIKE LOWER(:searchTerm) OR " +
            "p.id IN (SELECT c.project.id FROM Contributor c WHERE LOWER(c.employees.name) LIKE LOWER(:searchTerm))")
    List<Project> findByTitleOrPmOrContributor(String searchTerm);

    @Query("SELECT p FROM Project p WHERE " +
            "LOWER(p.projTitle) LIKE LOWER(:term) OR " +
            "LOWER(p.projPm) LIKE LOWER(:term) OR " +
            "p.id IN (SELECT c.project.id FROM Contributor c WHERE LOWER(c.employees.name) LIKE LOWER(:term))")
    List<Project> findByTermWithLimit(String term, Pageable pageable);
}