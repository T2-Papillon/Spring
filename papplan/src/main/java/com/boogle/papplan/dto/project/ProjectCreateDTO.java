package com.boogle.papplan.dto.project;

import com.boogle.papplan.dto.EmployeeDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProjectCreateDTO {

    //private Integer projNo;
    private String projTitle;
    //private String projPm;
    private Integer projPmEno;
    //private String projPmDept;
    private Date projStartDate;
    private Date projEndDate;
    //private Integer projPercent;
    private Date projCreateDate;
    private String projDesc;
    private String projectPriority;
    private String projectStatus;
    private List<Integer> contributors;
}
