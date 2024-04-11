package com.boogle.papplan.repository;

import com.boogle.papplan.dto.project.ProjectQueryDTO;
import com.boogle.papplan.entity.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    // PM으로 참여한 프로젝트를 조회하는 메서드
    @Query("SELECT p FROM Project p WHERE LOWER(p.projPm.name) LIKE LOWER(:projPm)")
    List<Project> findByProjPm(String projPm);

    // 프로젝트에 참여자로서 포함된 프로젝트를 조회하는 메서드
    @Query("SELECT c.project FROM Contributor c WHERE c.employees.id = :id")
    List<Project> findProjectsByContributorId(@Param("id") Long id);

    // 프로젝트 상태로 프로젝트를 조회하는 메서드
    List<Project> findByProjectStatus_ProjectStatusId(String projectStatusId);

    // 프로젝트명 또는 PM 또는 참여자로 프로젝트 검색
    @Query("SELECT p FROM Project p WHERE " +
            " LOWER(p.projTitle) LIKE LOWER(:term) OR " +
            // " LOWER(p.projPm) LIKE LOWER(:term) OR " +
            " LOWER(p.projPm.name) LIKE LOWER(:term) OR " +
            " p.id IN (SELECT c.project.id FROM Contributor c WHERE LOWER(c.employees.name) LIKE LOWER(:term))")
    List<Project> findByTermWithPage(String term, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Project p" +
            " JOIN Contributor c ON p.projNo = c.project.projNo WHERE " +
            " c.employees.eno = (:empno) OR " +
            // " p.projPm = (SELECT e.name from Employees e WHERE (:empno) = e.eno)")
            " p.projPm.eno = (:empno) ")
    List<Project> findAllByEmpno(Integer empno);

    // 프로젝트 번호(projNo)에 해당하는 프로젝트를 조회하는 메서드
    Project findByProjNo(Integer projNo);

    /*
    // 키워드에 대한 전체 프로젝트 조회 + 해당 프로젝트의 참여중인 직원의 정보 조인 쿼리
    // 추후 대용량 데이터 처리 시 쿼리를 한 개로 줄일 수도 있으므로 조인 쿼리 추가
    @Query("SELECT NEW com.boogle.papplan.dto.project.ProjectQueryDTO(p.projNo, p.projTitle, p.projPm, p.projStartDate, p.projEndDate, p.projPercent, p.projCreateDate, p.projDesc, p.projectStatus.projectStatusId, p.projectStatus.projectStatusId, e.eno, e.name, e.email, e.department.dept_no, e.position.position_id) " +
            "FROM Project p " +
            "JOIN p.contributors c " +
            "JOIN c.employees e " +
            "WHERE LOWER(p.projTitle) LIKE LOWER(:term) OR LOWER(p.projPm) LIKE LOWER(:term) OR LOWER(e.name) LIKE LOWER(:term)")
    List<ProjectQueryDTO> findByTerm(String term, Pageable pageable);*/
}
