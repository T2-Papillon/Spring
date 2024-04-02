package com.boogle.papplan.controller;

import com.boogle.papplan.dto.project.ProjectDTO;
import com.boogle.papplan.dto.TaskDTO;
import com.boogle.papplan.service.project.ProjectService;
import com.boogle.papplan.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project")
@CrossOrigin("http://localhost:5173/")
public class ProjectController {

    private final ProjectService projectService;
    private final TaskService taskService;

    @Autowired
    public ProjectController(ProjectService projectService, TaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
    }

    // PM별 프로젝트 조회
    @GetMapping("/pm/{pm}")
    public ResponseEntity<List<ProjectDTO>> getProjectsByPM(@RequestParam String pm) {
        List<ProjectDTO> projectDTOS = projectService.getProjectsByPM(pm);
        return ResponseEntity.ok().body(projectDTOS);
    }

    // 참여자별 프로젝트 조회
    @GetMapping("/contributor/{id}")
    public ResponseEntity<List<ProjectDTO>> getProjectsByContributor(@RequestParam Integer empNo) {
        List<ProjectDTO> projectDTOS = projectService.findProjectsByEmpNo(empNo);
        return ResponseEntity.ok().body(projectDTOS);
    }

    // 상태별 프로젝트 조회
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ProjectDTO>> getProjectsByStatus(@RequestParam("status") String status) {
        List<ProjectDTO> projectDTOS = projectService.getProjectsByStatus(status);
        return ResponseEntity.ok().body(projectDTOS);
    }

    // 프로젝트 검색
    @GetMapping("/search")
    public ResponseEntity<List<ProjectDTO>> searchProjects(@RequestParam String term,
                                                           @RequestParam(defaultValue = "0") String page,
                                                           @RequestParam(defaultValue = "10") String pageSize) {

        int pageInt = Integer.parseInt(page);
        int pageSizeInt = Integer.parseInt(pageSize);

        List<ProjectDTO> projectDTOS = projectService.searchProjects(term,pageInt,pageSizeInt);
        return ResponseEntity.ok(projectDTOS);
    }

    // 프로젝트 상세정보 조회
    @GetMapping("/{projNo}")
    public ResponseEntity<ProjectDTO> showProjectDetail(@RequestParam("projNo") Integer projNo) {
        ProjectDTO projectDto = projectService.getProjectByProjNo(projNo);

        if (projectDto == null) {
            // 프로젝트가 존재하지 않을 경우 404 Not Found 응답 반환
            return ResponseEntity.notFound().build();
        }

        // 프로젝트 정보를 JSON 형식으로 반환
        return ResponseEntity.ok().body(projectDto);
    }

    // 모든 프로젝트 목록 조회
    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        List<ProjectDTO> projectDTOS = projectService.getAllProjects();
        return ResponseEntity.ok(projectDTOS);
    }

    // 특정 프로젝트에 속한 모든 Task 조회
    @GetMapping("/{projectNo}/task")
    public ResponseEntity<List<TaskDTO>> getTasksByProjectId(@RequestParam Integer projectNo) {
        List<TaskDTO> tasks = taskService.getTasksByProjectId(projectNo);
        return ResponseEntity.ok(tasks);
    }
}
