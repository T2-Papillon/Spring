package com.boogle.papplan.dto;

import lombok.Data;

import java.util.List;

// StatisticProjectDto와 StatisticTaskStatusDto를 하나로 합체!
@Data
public class StatisticCombinedDTO {
    private StatisticProjectDTO projectDetails;
    private List<StatisticTaskStatusDTO> taskStatusCounts;

    // 생성자
    public StatisticCombinedDTO(StatisticProjectDTO projectDetails, List<StatisticTaskStatusDTO> taskStatusCounts) {
        this.projectDetails = projectDetails;
        this.taskStatusCounts = taskStatusCounts;
    }
}
