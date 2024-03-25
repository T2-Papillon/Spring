package com.boogle.papplan.controller;

import com.boogle.papplan.entity.Project;
import com.boogle.papplan.entity.ProjectStatus;
import com.boogle.papplan.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/pm/{pm}")
    public ResponseEntity<List<Project>> getProjectsByPM(@PathVariable String pm) {
        List<Project> projects = projectService.getProjectsByPM(pm);
        return ResponseEntity.ok().body(projects);
    }

    @GetMapping("/contributor/{id}")
    public ResponseEntity<List<Project>> getProjectsByContributor(@PathVariable Long id) {
        List<Project> projects = projectService.findProjectsByContributorId(id);
        return ResponseEntity.ok().body(projects);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Project>> getProjectsByStatus(@PathVariable("status") String status) {
        List<Project> projects = projectService.getProjectsByStatus(status);
        return ResponseEntity.ok().body(projects);
    }

    // 검색 api 수정
    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProjects(@RequestParam("keyword") String keyword) {
        List<Project> searchResults = projectService.searchProjects(keyword);
        return ResponseEntity.ok().body(searchResults);
    }


    // 프로젝트 상세정보 조회
    @GetMapping("/{projNo}")
    public ResponseEntity<Project> showProjectDetail(@PathVariable("projNo") Integer projNo) {
        // 프로젝트 번호(projNo)에 해당하는 프로젝트 정보 조회
        Project project = projectService.getProjectByProjNo(projNo);

        if (project == null) {
            // 프로젝트가 존재하지 않을 경우 404 Not Found 응답 반환
            return ResponseEntity.notFound().build();
        }

        // 프로젝트 정보를 JSON 형식으로 반환
        return ResponseEntity.ok().body(project);
    }
}
