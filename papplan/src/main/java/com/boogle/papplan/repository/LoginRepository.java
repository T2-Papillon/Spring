package com.boogle.papplan.repository;

import com.boogle.papplan.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Employees, Integer> {
}
