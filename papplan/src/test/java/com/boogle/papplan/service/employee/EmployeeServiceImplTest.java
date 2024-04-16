package com.boogle.papplan.service.employee;

import com.boogle.papplan.entity.Department;
import com.boogle.papplan.entity.Employees;
import com.boogle.papplan.entity.Position;
import com.boogle.papplan.repository.DepartmentRepository;
import com.boogle.papplan.repository.EmployeeRepository;
import com.boogle.papplan.repository.PositionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
class EmployeeServiceImplTest {

  @Autowired
  private EmployeeRepository employeeRepository;
  @Autowired
  private DepartmentRepository departmentRepository;
  @Autowired
  private PositionRepository positionRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  Department department = new Department();
  Position position = new Position();

  @BeforeEach
  void beforeEmployee() {
    department.setDept_no("IT");
    department.setDept_name("IT");
    departmentRepository.save(department);

    position.setPosition_id("TEMP");
    position.setPosition_name("TEMP");
    positionRepository.save(position);
  }

  @Test
  @Transactional
  @DisplayName("이메일로 사용자 정보 확인")
  void findEmployeeByEmail() {

    //given
    String email = "1234@boogle.co.kr";
    String pw = "12345";
    String encrypt = passwordEncoder.encode(pw);

    Employees employees = new Employees();
    employees.setEmail(email);
    employees.setPassword(encrypt);
    employees.setName("choi");
    employees.setDepartment(department);
    employees.setPosition(position);
    employeeRepository.save(employees);

    //when
    Optional<Employees> emp = employeeRepository.findByEmail(email);

    //then
    Assertions.assertNotNull(emp);
    Assertions.assertTrue(passwordEncoder.matches(pw, emp.get().getPassword()));
  }
}