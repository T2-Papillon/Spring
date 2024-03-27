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

    @GetMapping("/project2")
    public ResponseEntity<List> searchByPage(@RequestParam String term,
                                             @RequestParam(defaultValue = "0") String page,
                                             @RequestParam(defaultValue = "10") String pageSize) {

        int pageInt = Integer.parseInt(page);
        int pageSizeInt = Integer.parseInt(pageSize);

        List<Project> projects = searchService.searchProjectPages(term, pageInt, pageSizeInt);

        return ResponseEntity.ok(projects);
    }
}