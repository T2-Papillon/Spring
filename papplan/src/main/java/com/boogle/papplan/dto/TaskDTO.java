package com.boogle.papplan.dto;

import com.boogle.papplan.entity.Employees;
import com.boogle.papplan.entity.TaskPriority;
import com.boogle.papplan.entity.TaskStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import java.util.Date;

@Data
public class TaskDTO {

    @JsonProperty("task_no")
    private Integer taskNo;

    @JsonProperty(value = "proj_no", required = true)
    private Integer projNo;

    @JsonProperty("task_title")
    private String taskTitle;

    @JsonProperty("assignee_eno")
    private Integer assigneeEno; // 담당자의 사번

    @JsonProperty("assignee_name")
    private String assigneeName;

    @JsonProperty("assignee_dept")
    private String assigneeDept;

    @JsonProperty("task_status")
    private String taskStatus;

    @JsonProperty("task_priority")
    private String taskPriority;

    @JsonProperty("start_date")
    private Date taskStartDate;

    @JsonProperty("end_date")
    private Date taskEndDate;

    @JsonProperty("finish_date")
    private Date taskFinishDate;

    @JsonProperty("task_percent")
    private Integer taskPercent;

    @JsonProperty("task_test")
    private Boolean taskTest;

    @JsonProperty("task_test_url")
    private String taskTestUrl;

    @JsonProperty("update_date")
    private Date taskUpdateDate;

    @JsonProperty("create_date")
    private Date taskCreateDate;

    @JsonProperty("task_desc")
    private String taskDesc;


}