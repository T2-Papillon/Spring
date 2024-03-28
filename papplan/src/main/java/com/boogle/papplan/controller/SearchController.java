package com.boogle.papplan.controller;

import com.boogle.papplan.dto.ProjectDTO;
import com.boogle.papplan.service.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> findProjectsByStatusId(@RequestParam(required = false) String projectStatusId) {
        List<ProjectDTO> projectDTOS = searchService.findProjectsByStatusIdDto(projectStatusId);
        return ResponseEntity.ok(projectDTOS);
    }

    @GetMapping("/project2")
    public ResponseEntity<List<ProjectDTO>> searchByPage(@RequestParam String term,
                                             @RequestParam(defaultValue = "0") String page,
                                             @RequestParam(defaultValue = "10") String pageSize) {

        int pageInt = Integer.parseInt(page);
        int pageSizeInt = Integer.parseInt(pageSize);

        List<ProjectDTO> projects = searchService.searchProjects(term, pageInt, pageSizeInt);

        return ResponseEntity.ok(projects);
    }
}