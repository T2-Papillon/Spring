package com.boogle.papplan.controller;

import com.boogle.papplan.dto.StatisticProjectDto;
import com.boogle.papplan.service.ProjectService;
import com.boogle.papplan.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatisticController {
    private final StatisticService statisticService;

    @Autowired
    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("/{projNo}/details")
    public StatisticProjectDto getProjectDetails(@PathVariable Integer projNo) {
        return statisticService.getProjectDetails(projNo);
    }
}
