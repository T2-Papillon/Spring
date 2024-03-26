package com.boogle.papplan.controller;

import com.boogle.papplan.entity.Project;
import com.boogle.papplan.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/projects")
    public List<Project> findProjectsByStatusId(@RequestParam(required = false) String projectStatusId) {
        return searchService.findProjectsByStatusId(projectStatusId);
    }
}
