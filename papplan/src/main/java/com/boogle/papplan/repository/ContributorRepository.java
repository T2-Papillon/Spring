package com.boogle.papplan.repository;

import com.boogle.papplan.dto.employee.EmployeeDTO;
import com.boogle.papplan.entity.Contributor;
import com.boogle.papplan.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContributorRepository extends JpaRepository<Contributor, Long> {

    // 특정 사용자의 Contributor 엔터티를 가져오는 메서드
    Optional<Contributor> findById(Long id);

    @Query("SELECT new com.boogle.papplan.dto.employee.EmployeeDTO(e.eno, e.email, e.name, e.department.dept_no, e.position.position_id) " +
            " FROM Contributor c " +
            " JOIN c.employees e " +
            " WHERE c.project.projNo = :projNo ")
    List<EmployeeDTO> findContributors(Integer projNo);

    // 이름으로 참여한 프로젝트를 조회하는 메서드
    @Query("SELECT c.project FROM Contributor c WHERE c.employees.name = :name")
    List<Project> findProjectsByContributorName(@Param("name") String name);

}
