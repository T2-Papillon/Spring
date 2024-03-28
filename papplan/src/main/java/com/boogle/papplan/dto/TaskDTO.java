package com.boogle.papplan.dto;

import com.boogle.papplan.entity.TaskPriority;
import com.boogle.papplan.entity.TaskStatus;
import lombok.Data;
import java.util.Date;

@Data
public class TaskDTO {
    private Integer taskNo;
    private String taskTitle;
    private String assignee;
    private String taskDesc;
    private TaskStatus taskStatus;
    private TaskPriority taskPriority;
    private Date taskStartDate;
    private Date taskEndDate;
}