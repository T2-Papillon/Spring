package com.boogle.papplan.dto;

import lombok.Data;

@Data
public class StatisticProjectDto {
    private Long projNo;
    private String title;
    private String startDate;
    private String endDate;
    private String participants;
    private Integer progress;
    private String createDate;
}
