package com.boogle.papplan.dto;

import lombok.Data;

@Data
public class StatisticTaskStatusDto {
    private String taskStatusName;
    private long taskCount;

    public StatisticTaskStatusDto(String taskStatusName, Long taskCount) {
    }
}