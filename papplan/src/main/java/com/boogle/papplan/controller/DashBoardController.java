package com.boogle.papplan.controller;

import com.boogle.papplan.dto.TaskDTO;
import com.boogle.papplan.dto.project.ProjectDTO;
import com.boogle.papplan.service.project.ProjectService;
import com.boogle.papplan.service.task.TaskService;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<HashMap<String,Object>> empProjectTaskAll(@PathVariable("empno") Integer empNo){
        HashMap<String, Object> empDashBoard = new HashMap<>();
        List<ProjectDTO> projects = projectService.findProjectsByEmpNo(empNo);
        List<TaskDTO> tasks = taskService.getTasksByEmpNoInProgress(empNo);
        empDashBoard.put("projects", projects);
        empDashBoard.put("tasks", tasks);

        return ResponseEntity.ok(empDashBoard);
    }

    // 사용자 프로젝트 상세 조회
    // 24.03.31 : 사용자 전체 프로젝트 조회
    @GetMapping("/emp/{empno}/projects")
    public ResponseEntity<List<ProjectDTO>> empProjectsAll(@PathVariable("empno") Integer empNo){
        List<ProjectDTO> projectDTOS = projectService.findProjectsByEmpNo(empNo);
        return ResponseEntity.ok().body(projectDTOS);
    }

    // 프로젝트의 상세 정보와 하위 업무 조회
    @GetMapping("/projects/{projNo}/tasks")
    public ResponseEntity<HashMap<String,Object>> getTaskById(@PathVariable Integer projNo) {
        HashMap<String,Object> prjDashBoard = new HashMap<>();
        ProjectDTO project = projectService.getProjectByProjNo(projNo);
        List<TaskDTO> tasks = taskService.getTasksByProjectId(projNo);
        prjDashBoard = objectMapper.convertValue(project,HashMap.class);
        prjDashBoard.put("tasks", tasks);
        return ResponseEntity.ok().body(prjDashBoard);
    }

}
