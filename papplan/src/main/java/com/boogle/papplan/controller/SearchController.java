package com.boogle.papplan.controller;

import com.boogle.papplan.dto.ProjectDto;
import com.boogle.papplan.entity.Project;
import com.boogle.papplan.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public ResponseEntity<List<ProjectDto>> findProjectsByStatusId(@RequestParam(required = false) String projectStatusId) {
        List<ProjectDto> projectDtos = searchService.findProjectsByStatusIdDto(projectStatusId);
        return ResponseEntity.ok(projectDtos);
    }

    @GetMapping("/project")
    public ResponseEntity<List<ProjectDto>> searchProjects(@RequestParam String searchTerm) {
        List<ProjectDto> projectDtos = searchService.searchProjectsDto(searchTerm);
        return ResponseEntity.ok(projectDtos);
    }
}