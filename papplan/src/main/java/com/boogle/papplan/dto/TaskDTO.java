package com.boogle.papplan.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TaskDTO {

    private Integer taskNo;
    private String taskTitle;
    private String taskDesc;
    private String taskStatus;
    private Date taskEndDate;

}
