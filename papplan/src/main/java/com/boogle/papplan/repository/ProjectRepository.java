package com.boogle.papplan.repository;

import com.boogle.papplan.dto.project.ProjectQueryDTO;
import com.boogle.papplan.entity.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    // PM으로 참여한 프로젝트를 조회하는 메서드
    @Query("SELECT p FROM Project p WHERE LOWER(p.projPm.name) LIKE LOWER(:projPm)")
    List<Project> findByProjPm(String projPm);

    // 프로젝트 상태로 프로젝트를 조회하는 메서드
    List<Project> findByProjectStatus_ProjectStatusId(String projectStatusId);

    // 프로젝트명 또는 PM 또는 참여자로 프로젝트 검색
    @Query("SELECT p FROM Project p WHERE " +
            " LOWER(p.projTitle) LIKE LOWER(:term) OR " +
            // " LOWER(p.projPm) LIKE LOWER(:term) OR " +
            " LOWER(p.projPm.name) LIKE LOWER(:term) OR " +
            " p.id IN (SELECT c.project.id FROM Contributor c WHERE LOWER(c.employees.name) LIKE LOWER(:term))")
    List<Project> findByTermWithPage(String term, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Project p " +
            " JOIN FETCH p.projPm " +
            "JOIN p.contributors c " +
            "WHERE c.employees.eno = (:empno) OR  p.projPm.eno = (:empno) ")
    List<Project> findAllByEmpno(Integer empno);

    // 프로젝트 번호(projNo)에 해당하는 프로젝트를 조회하는 메서드
    @Query(" SELECT p FROM Project p " +
            "JOIN FETCH p.projPm " +
            "JOIN p.contributors c " +
            "WHERE p.projNo = :projNo ")
    Optional<Project> findByProjNo(Integer projNo);

    @Query(" SELECT DISTINCT p FROM Project p " +
            "JOIN FETCH p.projPm " +
            "JOIN p.contributors c ")
    List<Project> findAllWithLazy();

    boolean existsByProjectStatus_ProjectStatusId(String projectStatusId);
}
