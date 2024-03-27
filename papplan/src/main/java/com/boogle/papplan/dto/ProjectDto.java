package com.boogle.papplan.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProjectDto {

    private Integer projNo;
    private String projTitle;
    private String projPm;
    private Date projStartDate;
    private Date projEndDate;
    private Integer projPercent;
    private Date projCreateDate;
    private String projDesc;
    private String projectPriority;
    private String projectStatus;
    private List<Integer> employeeEnos; // 참여자의 사원 번호(eno) 리스트

}
