package com.boogle.papplan.controller;

import com.boogle.papplan.entity.Department;
import com.boogle.papplan.entity.Employees;
import com.boogle.papplan.entity.Position;
import com.boogle.papplan.service.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final EmployeeService employeeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public LoginController(EmployeeService employeeService){

        this.employeeService = employeeService;
    }

    @ResponseBody
    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody HashMap<String,String> userInfo) {
        boolean isSignIn = employeeService.signInLogin(userInfo);
        if(isSignIn)
            return ResponseEntity.ok("Login successful");
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Login failed");
    }

    @ResponseBody
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody HashMap<String,String> emp) {
        System.out.println("signup");

        Employees employees = new Employees();
        employees.setName(emp.get("name"));
        employees.setEmail(emp.get("email"));
        employees.setPassword(passwordEncoder.encode(emp.get("password")));

        Department department = new Department();
        department.setDept_no(emp.get("dept_no"));
        employees.setDepartment(department);

        Position position = new Position();
        position.setPosition_id(emp.get("position_id"));
        employees.setPosition(position);

        try{
            employeeService.signUp(employees);
        }
        catch(DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("id가 중복되었습니다.");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}