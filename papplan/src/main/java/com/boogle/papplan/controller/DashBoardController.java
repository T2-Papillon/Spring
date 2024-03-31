package com.boogle.papplan.controller;

import com.boogle.papplan.dto.TaskDTO;
import com.boogle.papplan.dto.project.ProjectDTO;
import com.boogle.papplan.service.project.ProjectService;
import com.boogle.papplan.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashBoardController {

    private final ProjectService projectService;
    private final TaskService taskService;

    @Autowired
    public DashBoardController(ProjectService projectService,
                                TaskService taskService)
    {
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @GetMapping("/emp/{empno}")
    public ResponseEntity<HashMap<String,Object>> empProjectTaskAll(@PathVariable("empno") Integer empNo){
        HashMap<String, Object> empDashBoard = new HashMap<>();
        List<ProjectDTO> projects = projectService.findProjectsByEmpNo(empNo);
        List<TaskDTO> tasks = taskService.getTasksByEmpNo(empNo);
        empDashBoard.put("projects", projects);
        empDashBoard.put("tasks", tasks);

        return ResponseEntity.ok(empDashBoard);
    }

}
