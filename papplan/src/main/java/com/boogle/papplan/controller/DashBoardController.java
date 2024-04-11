package com.boogle.papplan.controller;

import com.boogle.papplan.dto.TaskDTO;
import com.boogle.papplan.dto.project.ProjectDTO;
import com.boogle.papplan.service.project.ProjectService;
import com.boogle.papplan.service.task.TaskService;
import com.boogle.papplan.util.DashBoardDataUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin("http://localhost:5173/")
public class DashBoardController {

    private final ProjectService projectService;
    private final TaskService taskService;
    private final ObjectMapper objectMapper;

    @Autowired
    public DashBoardController(ProjectService projectService,
                               TaskService taskService,
                               ObjectMapper objectMapper)
    {
        this.projectService = projectService;
        this.taskService = taskService;
        this.objectMapper = objectMapper;
    }

    // 사용자 첫 화면 -> 진행중인 프로젝트 + 진행중인 업무
    // 24.03.31 : 사용자가 참여중인 프로젝트, 진행중인 업무 조회
    // 통계 추가 예정
    @GetMapping("/emp/{empno}")
    public ResponseEntity<Object> empProjectTaskAll(@PathVariable("empno") Integer empNo){
        HashMap<String, Object> empDashBoard = new HashMap<>();

        try{
            //List<ProjectDTO>    projects   = projectService.findProjectsByEmpNo(empNo);
            //List<TaskDTO>       tasks      = taskService.getTasksByEmpNo(empNo);

            Optional<HashMap<String,Object>> projectData  = projectService.findProjectsByEmpNoDashBoard(empNo);
            Optional<HashMap<String,Object>> taskData     = taskService.getTasksByEmpNoDashBoard(empNo);

            projectData.ifPresent(empDashBoard::putAll);

            taskData.ifPresent(empDashBoard::putAll);

            return ResponseEntity.ok(empDashBoard);
        }
        catch(Exception e){
            return new ResponseEntity<>("Data Not Found", HttpStatus.NOT_FOUND);
        }
    }

}
