package com.boogle.papplan.dto;

import lombok.Data;

@Data
public class StatisticTaskStatusDTO {
    private String taskStatusName;
    private long taskCount;

    public StatisticTaskStatusDTO(String taskStatusName, Long taskCount) {
    }
}
