package com.boogle.papplan.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_no")
    private Integer taskNo; // 업무번호

    // 외래키
    @ManyToOne
    @JoinColumn(name="proj_no", nullable = false)
    private Project project; // 프로젝트번호

    @Column(name = "task_title", length=50, nullable = false)
    private String taskTitle; // 업무명

    @Column(name = "assignee", length=20, nullable = false)
    private String assignee; // 담당자

    @Temporal(TemporalType.DATE)
    @Column(name = "task_start_date", nullable = false)
    private Date taskStartDate; // 업무시작일

    @Temporal(TemporalType.DATE)
    @Column(name = "task_end_date", nullable = false)
    private Date taskEndDate; // 업무종료일

    @Column(name = "task_percent", nullable = false)
    private Integer taskPercent; // 업무진행율

    @Temporal(TemporalType.DATE)
    @Column(name = "task_create_date", nullable = false)
    private Date taskCreateDate; // 업무작성일

    @Column(name = "task_test", nullable = false)
    private Boolean taskTest; // 테스트 진행여부

    @Temporal(TemporalType.DATE)
    @Column(name = "task_update_date", nullable = true)
    private Date taskUpdateDate; // 업무수정일

    @Column(name = "task_desc", length=300, nullable = false)
    private String taskDesc; // 설명

    // 외래키
    @OneToOne
    @JoinColumn(name = "tasks_no", nullable = false)
    private TaskStatusEntity taskStatus; // 업무상태번호

    // 외래키
    @OneToOne
    @JoinColumn(name = "taskp_no", nullable = false)
    private TaskPriorityEntity taskPriority; // 업무우선순위번호

}
