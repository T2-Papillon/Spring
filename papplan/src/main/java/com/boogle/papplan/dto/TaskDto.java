package com.boogle.papplan.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class TaskDto {

    private Integer taskNo;
    private String taskTitle;
    private String taskDesc;
    private String taskStatus;
    private Date taskEndDate;

}
