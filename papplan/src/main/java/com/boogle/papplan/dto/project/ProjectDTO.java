package com.boogle.papplan.dto.project;

import com.boogle.papplan.dto.employee.EmployeeDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProjectDTO {

    private Integer projNo;
    private String projTitle;
    private String projPm;
    private Integer projPmEno;
    private String projPmDept;
    private Date projStartDate;
    private Date projEndDate;
    private Integer projPercent;
    private Date projCreateDate;
    private String projDesc;
    private String projectPriority;
    private String projectStatus;
    private List<EmployeeDTO> contributors; // 참여자의 사원 번호(eno) 리스트

}
