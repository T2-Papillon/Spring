package com.boogle.papplan.controller;

import com.boogle.papplan.dto.StatisticCombinedDTO;
import com.boogle.papplan.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/statistic")
@CrossOrigin("http://localhost:5173/")
public class StatisticController {
    private final StatisticService statisticService;

    @Autowired
    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("/{projNo}/details")
    public ResponseEntity<StatisticCombinedDTO> getCombinedProjectInfo(@PathVariable("projNo") Integer projNo) {
        StatisticCombinedDTO combinedDto = statisticService.getCombinedProjectInfo(projNo);
        return ResponseEntity.ok(combinedDto);
    }

//    @GetMapping("/{projNo}/tasks/status")
//    public ResponseEntity<List<StatisticTaskStatusDto>> getTaskCountByStatus(@PathVariable("projNo") Integer projNo) {
//        List<StatisticTaskStatusDto> statisticTaskStatusDtos = statisticService.getTaskCountByStatus(projNo);
//        return ResponseEntity.ok().body(statisticTaskStatusDtos);
//    }
}
