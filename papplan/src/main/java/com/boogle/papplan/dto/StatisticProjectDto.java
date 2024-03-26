package com.boogle.papplan.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter @Setter
@ToString
public class StatisticProjectDto {
    private Integer projNo;
    private String title;
    private String startDate;
    private String endDate;
//    private String participants;
    private Integer progress;
    private String createDate;

    public StatisticProjectDto(Integer projNo, String title, String startDate, String endDate, Integer progress, String createDate) {
        this.projNo = projNo;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.progress = progress;
        this.createDate = createDate;
    }
}
