package com.boogle.papplan.controller;

import com.boogle.papplan.entity.Project;
import com.boogle.papplan.entity.ProjectStatus;
import com.boogle.papplan.service.ProjectListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectListController {

    private final ProjectListService projectListService;

    @Autowired
    public ProjectListController(ProjectListService projectListService) {
        this.projectListService = projectListService;
    }

    @GetMapping("/pm/{pm}")
    public ResponseEntity<List<Project>> getProjectsByPM(@PathVariable String pm) {
        List<Project> projects = projectListService.getProjectsByPM(pm);
        return ResponseEntity.ok().body(projects);
    }

    @GetMapping("/contributor/{id}")
    public ResponseEntity<List<Project>> getProjectsByContributor(@PathVariable Long id) {
        List<Project> projects = projectListService.getProjectsByContributor(id);
        return ResponseEntity.ok().body(projects);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Project>> getProjectsByStatus(@PathVariable String status) {
        ProjectStatus projectStatus = new ProjectStatus();
        projectStatus.setProjectStatusId(status);
        List<Project> projects = projectListService.getProjectsByStatus(projectStatus);
        return ResponseEntity.ok().body(projects);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProjects(@RequestParam("keyword") String keyword) {
        List<Project> searchResults = projectListService.searchProjects(keyword);
        return ResponseEntity.ok().body(searchResults);
    }
}
