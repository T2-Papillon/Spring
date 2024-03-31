package com.boogle.papplan.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proj_no")
    private Integer projNo; // 프로젝트 번호

    @Column(length = 50, nullable = false, name = "proj_title")
    private String projTitle; // 프로젝트명

    @Column(length = 50, nullable = false, name = "proj_pm")
    private String projPm; // PM

    @Temporal(TemporalType.DATE)
    @Column(nullable = false, name = "proj_start_date")
    private Date projStartDate; // 프로젝트 시작일

    @Temporal(TemporalType.DATE)
    @Column(nullable = false, name = "proj_end_date")
    private Date projEndDate; // 프로젝트 종료일

    @Column(name = "proj_percent")
    private Integer projPercent; // 프로젝트 진행율

    @Temporal(TemporalType.DATE)
    @Column(nullable = false, name = "proj_create_date")
    private Date projCreateDate; // 프로젝트 작성일

    @Column(length = 300, nullable = false, name = "proj_desc")
    private String projDesc; // 프로젝트 설명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "projp_no")
    private ProjectPriority projectPriority; // 프로젝트 우선순위번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "projs_no")
    private ProjectStatus projectStatus; // 프로젝트 상태번호

    // Task 엔터티와의 @OneToMany 관계 추가
    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

    // contributor 엔터티와의 @OneToMany 관계 추가
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<Contributor> contributors;

}
