package com.boogle.papplan.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ProjectQueryDTO {

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
    private Integer eno;
    private String name;
    private String email;
    private String deptNo;
    private String positionId;
}
