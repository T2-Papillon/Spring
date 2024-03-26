package com.boogle.papplan.repository;

import com.boogle.papplan.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employees, Integer> {

    Optional<Employees> findByEmail(String email);
}
