package com.boogle.papplan.controller;

import com.boogle.papplan.entity.Project;
import com.boogle.papplan.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    // 통합 검색 페이지를 호출하면 모든 프로젝트 목록이 기본값으로
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> allProjects = searchService.getAllProjects();
        return ResponseEntity.ok().body(allProjects);
    }

    // 검색 기능을 제공하는 엔드포인트
    @GetMapping("/projects")
    public ResponseEntity<List<Project>> searchProjects(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) List<String> status,
            @RequestParam(required = false) String orderBy) {
        List<Project> projects;
        if (keyword != null && !keyword.isEmpty()) {
            projects = searchService.searchProjects(keyword);
        } else if (status != null && !status.isEmpty()) {
            projects = searchService.filterProjectsByStatus(status);
        } else if (orderBy != null && !orderBy.isEmpty()) {
            projects = searchService.sortBy(orderBy);
        } else {
            projects = searchService.getAllProjects();
        }
        return ResponseEntity.ok().body(projects);
    }

//    // 상태별로 필터링하는 엔드포인트
//    @GetMapping("/projects/filter")
//    public ResponseEntity<List<Project>> filterProjectsByStatus(String projectStatusId) {
//        List<Project> filteredProjects = searchService.filterProjectsByStatus(projectStatusId);
//        return ResponseEntity.ok().body(filteredProjects);
//    }
//
//    // 최신순으로 정렬하는 엔드포인트
//    @GetMapping("/projects/sort/latest")
//    public ResponseEntity<List<Project>> sortByLatestProjects() {
//        List<Project> sortedProjects = searchService.sortByLatestProjects();
//        return ResponseEntity.ok().body(sortedProjects);
//    }
//
//    // 우선순위별로 정렬하는 엔드포인트
//    @GetMapping("/projects/sort/priority")
//    public ResponseEntity<List<Project>> sortByPriorityProjects() {
//        List<Project> sortedProjects = searchService.sortByPriorityProjects();
//        return ResponseEntity.ok().body(sortedProjects);
//    }
}
