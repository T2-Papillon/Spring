package com.boogle.papplan.repository;

import com.boogle.papplan.dto.EmployeeDTO;
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
    @Query(" SELECT new com.boogle.papplan.dto.EmployeeDTO(e.eno, e.email, e.name, e.department.dept_no, e.position.position_id) " +
            " FROM Employees e WHERE " +
            " e.eno IN (:enos)")
    Optional<List<EmployeeDTO>> findAllByEnos(List<Integer> enos);

    @Query("SELECT e FROM Employees e WHERE e.name = :name")
    Optional<Employees> findByName(String name);
}
