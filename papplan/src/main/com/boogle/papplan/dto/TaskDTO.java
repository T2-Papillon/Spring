package com.boogle.papplan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    //@JsonProperty("proj_no")
    private String assignee;

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