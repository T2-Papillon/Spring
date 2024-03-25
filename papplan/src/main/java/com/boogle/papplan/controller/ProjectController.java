package com.boogle.papplan.controller;

import com.boogle.papplan.entity.Project;
import com.boogle.papplan.entity.ProjectStatus;
import com.boogle.papplan.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
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
        List<Project> projects = projectService.getProjectsByContributor(id);
        return ResponseEntity.ok().body(projects);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Project>> getProjectsByStatus(@PathVariable String status) {
        ProjectStatus projectStatus = new ProjectStatus();
        projectStatus.setProjectStatusId(status);
        List<Project> projects = projectService.getProjectsByStatus(projectStatus);
        return ResponseEntity.ok().body(projects);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProjects(@RequestParam("keyword") String keyword) {
        List<Project> searchResults = projectService.searchProjects(keyword);
        return ResponseEntity.ok().body(searchResults);
    }


    //글 상세페이지
    @GetMapping("/project/{projNo}")
    public String showProjectDetail(@PathVariable("projNo") Integer projNo, Model model) {
        // 프로젝트 번호(projNo)에 해당하는 프로젝트 정보 조회
        Project project = projectService.getProjectById(projNo);

        if (project == null) {
            // 프로젝트가 존재하지 않을 경우 에러 페이지로 이동하거나 적절한 처리를 수행합니다.
            return "error"; // 에러 페이지 경로로 수정해주세요.
        }

        // 조회한 프로젝트 정보를 모델에 추가하여 뷰로 전달
        model.addAttribute("project", project);

        // 상세 페이지 뷰로 이동
        return "projectDetail"; // 상세 페이지 뷰 경로로 수정해주세요.
    }
}
