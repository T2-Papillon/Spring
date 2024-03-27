package com.boogle.papplan.controller;

import com.boogle.papplan.dto.ProjectDto;
import com.boogle.papplan.dto.TaskDto;
import com.boogle.papplan.service.project.ProjectService;
import com.boogle.papplan.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project")
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
    public ResponseEntity<List<ProjectDto>> getProjectsByPM(@RequestParam String pm) {
        List<ProjectDto> projectDtos = projectService.getProjectsByPM(pm);
        return ResponseEntity.ok().body(projectDtos);
    }

    // 참여자별 프로젝트 조회
    @GetMapping("/contributor/{id}")
    public ResponseEntity<List<ProjectDto>> getProjectsByContributor(@RequestParam Long id) {
        List<ProjectDto> projectDtos = projectService.findProjectsByContributorId(id);
        return ResponseEntity.ok().body(projectDtos);
    }

    // 상태별 프로젝트 조회
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ProjectDto>> getProjectsByStatus(@RequestParam("status") String status) {
        List<ProjectDto> projectDtos = projectService.getProjectsByStatus(status);
        return ResponseEntity.ok().body(projectDtos);
    }

    // 프로젝트 검색
    @GetMapping("/search")
    public ResponseEntity<List<ProjectDto>> searchProjects(@RequestParam String searchTerm) {
        List<ProjectDto> projectDtos = projectService.searchProjects(searchTerm);
        return ResponseEntity.ok(projectDtos);
    }

    // 프로젝트 상세정보 조회
    @GetMapping("/{projNo}")
    public ResponseEntity<ProjectDto> showProjectDetail(@RequestParam("projNo") Integer projNo) {
        ProjectDto projectDto = projectService.getProjectByProjNo(projNo);

        if (projectDto == null) {
            // 프로젝트가 존재하지 않을 경우 404 Not Found 응답 반환
            return ResponseEntity.notFound().build();
        }

        // 프로젝트 정보를 JSON 형식으로 반환
        return ResponseEntity.ok().body(projectDto);
    }

    // 모든 프로젝트 목록 조회
    @GetMapping
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        List<ProjectDto> projectDtos = projectService.getAllProjects();
        return ResponseEntity.ok(projectDtos);
    }

    // 특정 프로젝트에 속한 모든 Task 조회
    @GetMapping("/{projectNo}/task")
    public ResponseEntity<List<TaskDto>> getTasksByProjectId(@RequestParam Integer projectNo) {
        List<TaskDto> tasks = taskService.getTasksByProjectId(projectNo);
        return ResponseEntity.ok(tasks);
    }
}
