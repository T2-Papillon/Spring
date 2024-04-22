package com.boogle.papplan.service.employee;

import com.boogle.papplan.dto.employee.EmployeeDTO;
import com.boogle.papplan.entity.Employees;

import java.util.HashMap;
import java.util.Optional;

public interface EmployeeService {

    public Optional<EmployeeDTO> signInLogin(HashMap<String,String> userInfo);

    public void signUp(Employees employees);
}
