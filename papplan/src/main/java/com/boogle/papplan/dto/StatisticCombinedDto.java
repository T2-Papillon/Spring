package com.boogle.papplan.dto;

import lombok.Data;

import java.util.List;

// StatisticProjectDto와 StatisticTaskStatusDto를 하나로 합체!
@Data
public class StatisticCombinedDto {
    private StatisticProjectDto projectDetails;
    private List<StatisticTaskStatusDto> taskStatusCounts;

    // 생성자
    public StatisticCombinedDto(StatisticProjectDto projectDetails, List<StatisticTaskStatusDto> taskStatusCounts) {
        this.projectDetails = projectDetails;
        this.taskStatusCounts = taskStatusCounts;
    }
}
