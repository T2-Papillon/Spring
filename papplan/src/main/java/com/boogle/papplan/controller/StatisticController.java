package com.boogle.papplan.controller;

import com.boogle.papplan.dto.StatisticCombinedDto;
import com.boogle.papplan.dto.StatisticProjectDto;
import com.boogle.papplan.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistic")
public class StatisticController {
    private final StatisticService statisticService;

    @Autowired
    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("/{projNo}/details")
    public ResponseEntity<StatisticCombinedDto> getCombinedProjectInfo(@PathVariable("projNo") Integer projNo) {
        StatisticCombinedDto combinedDto = statisticService.getCombinedProjectInfo(projNo);
        return ResponseEntity.ok(combinedDto);
    }

//    @GetMapping("/{projNo}/tasks/status")
//    public ResponseEntity<List<StatisticTaskStatusDto>> getTaskCountByStatus(@PathVariable("projNo") Integer projNo) {
//        List<StatisticTaskStatusDto> statisticTaskStatusDtos = statisticService.getTaskCountByStatus(projNo);
//        return ResponseEntity.ok().body(statisticTaskStatusDtos);
//    }
}
