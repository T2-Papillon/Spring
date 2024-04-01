package com.boogle.papplan.controller;

import com.boogle.papplan.dto.EmployeeDTO;
import com.boogle.papplan.entity.Department;
import com.boogle.papplan.entity.Employees;
import com.boogle.papplan.entity.Position;
import com.boogle.papplan.service.employee.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final EmployeeService employeeService;
    private final ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public LoginController(EmployeeService employeeService,
                           ObjectMapper objectMapper
    )
    {
        this.employeeService = employeeService;
        this.objectMapper = objectMapper;
    }

    @ResponseBody
    @PostMapping("/signin")
    public ResponseEntity<Object> signIn(@RequestBody HashMap<String,String> loginInfo) {
        System.out.println(loginInfo);
        Optional<EmployeeDTO> isSignIn = employeeService.signInLogin(loginInfo);
        if(isSignIn.isPresent()) {
            try{
                return ResponseEntity.ok(isSignIn.get());
            }
            catch(Exception e){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Login failed - invalid User Info");
            }
        }
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Login failed");
    }

    @ResponseBody
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody HashMap<String,String> emp) {

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