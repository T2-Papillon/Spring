package com.boogle.papplan.controller;

import com.boogle.papplan.interfaces.StatisticProjectDto;
import com.boogle.papplan.interfaces.StatisticTaskStatusDto;
import com.boogle.papplan.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistics")
public class StatisticController {
    private final StatisticService statisticService;

    @Autowired
    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("/{projNo}/details")
    public ResponseEntity<StatisticProjectDto> getProjectDetails(@PathVariable("projNo") Integer projNo) {
        StatisticProjectDto statisticProjectDto = statisticService.getProjectDetails(projNo);
        return ResponseEntity.ok().body(statisticProjectDto);
    }
// commit
//    @GetMapping("/{projNo}/tasks/status")
//    public ResponseEntity<List<StatisticTaskStatusDto>> getTaskCountByStatus(@PathVariable("projNo") Integer projNo) {
//        List<StatisticTaskStatusDto> statisticTaskStatusDtos = statisticService.getTaskCountByStatus(projNo);
//        return ResponseEntity.ok().body(statisticTaskStatusDtos);
//    }
}
