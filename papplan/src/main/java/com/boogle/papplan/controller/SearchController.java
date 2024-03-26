package com.boogle.papplan.controller;

import com.boogle.papplan.entity.Project;
import com.boogle.papplan.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public List<Project> findProjectsByStatusId(@RequestParam(required = false) String projectStatusId) {
        return searchService.findProjectsByStatusId(projectStatusId);
    }

    @GetMapping("/projectSearch")
    public ResponseEntity<List<Project>> searchProjects(@RequestParam String searchTerm) {
        List<Project> projects = searchService.searchProjects(searchTerm);
        return ResponseEntity.ok(projects);
    }
}