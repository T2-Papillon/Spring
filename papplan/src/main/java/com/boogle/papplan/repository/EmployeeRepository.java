package com.boogle.papplan.repository;

import com.boogle.papplan.dto.employee.EmpSearchDTO;
import com.boogle.papplan.dto.employee.EmployeeDTO;
import com.boogle.papplan.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employees, Integer> {

    Optional<Employees> findByEmail(String email);

    // eno 리스트에 해당하는 직원 정보 조회
    @Query(" SELECT new com.boogle.papplan.dto.employee.EmployeeDTO(e.eno, e.email, e.name, e.department.dept_no, e.position.position_id) " +
            " FROM Employees e WHERE " +
            " e.eno IN (:enos)")
    Optional<List<EmployeeDTO>> findAllByEnos(List<Integer> enos);

    @Query("SELECT new com.boogle.papplan.dto.employee.EmpSearchDTO(e.eno, e.name, e.department.dept_no) " +
            " FROM Employees e WHERE " +
            " e.name LIKE CONCAT('%', :name, '%') ")
    Optional<List<EmpSearchDTO>> findAllByPattern(String name);
}
