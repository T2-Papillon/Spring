package com.boogle.papplan.service.employee;

import com.boogle.papplan.entity.Employees;
import com.boogle.papplan.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public boolean signInLogin(HashMap<String,String> userInfo) {

        Optional<Employees> emp = employeeRepository.findByEmail(userInfo.get("email"));
        if(emp.isPresent()) {
            if(passwordEncoder.matches(userInfo.get("password"), emp.get().getPassword())){
                return true;
            }
            return false;
        }
        else{
            return false;
        }
    }

    @Override
    public void signUp(Employees employees) {
        employeeRepository.save(employees);
    }
}
