package com.boogle.papplan.service.employee;

import com.boogle.papplan.entity.Employees;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

public interface EmployeeService {

    public Optional<Employees> signInLogin(HashMap<String,String> userInfo);

    public void signUp(Employees employees);
}
