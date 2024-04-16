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
@RequestMapping("/api/mypage")
public class MyPageController {

    private final ProjectService projectService;
    private final TaskService taskService;

    @Autowired
    public MyPageController(ProjectService projectService, TaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
    }

    // 사용자의 마이페이지 렌더링에 필요한 데이터 API
    @GetMapping("/emp/{empno}")
    public ResponseEntity<HashMap<String,Object>> getMyPageData(@PathVariable("empno") Integer empNo) {
        HashMap<String, Object> mypageData = new HashMap<>();
        List<TaskDTO> tasks;
        List<ProjectDTO> projects;
        try{
            projects = projectService.findProjectsByEmpNo(empNo);
            tasks = taskService.getTasksByEmpNo(empNo);

            mypageData.put("projects", projects);
            mypageData.put("tasks", tasks);
        }
        catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(mypageData);
    }

}
