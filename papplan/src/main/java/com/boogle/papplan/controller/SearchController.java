package com.boogle.papplan.controller;

import com.boogle.papplan.dto.employee.EmpSearchDTO;
import com.boogle.papplan.dto.project.ProjectDTO;
import com.boogle.papplan.service.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@CrossOrigin("http://localhost:5173/")
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

    @GetMapping("/project")
    public ResponseEntity<List<ProjectDTO>> searchByPage(@RequestParam String term,
                                             @RequestParam(defaultValue = "0") String page,
                                             @RequestParam(defaultValue = "10") String pageSize) {

        int pageInt = Integer.parseInt(page);
        int pageSizeInt = Integer.parseInt(pageSize);

        List<ProjectDTO> projects = searchService.searchProjects(term, pageInt, pageSizeInt);

        return ResponseEntity.ok(projects);
    }
    
    @GetMapping("/emp")
    public ResponseEntity<Object> searchEmployee(@RequestParam("name") String pattern) {
        List<EmpSearchDTO> empList;
        try{
            empList = searchService.findEmployeeByName(pattern);
            return ResponseEntity.ok(empList);
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("검색 오류");
        }

    }

}