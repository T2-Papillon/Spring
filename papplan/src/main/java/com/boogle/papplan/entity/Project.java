package com.boogle.papplan.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proj_no")
    private Integer projNo; // 프로젝트 번호

    @Column(length = 50, nullable = false, name = "proj_name")
    private String projName; // 프로젝트명

    @Column(length = 50, nullable = false, name = "proj_pm")
    private String projPm; // PM

    @Column(length = 20, nullable = false, name = "proj_mem")
    private String projMem; // 프로젝트 참여자

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

    @OneToOne
    @JoinColumn(nullable = false, name = "projp_no")
    @Enumerated(EnumType.STRING) // Enum을 문자열로 저장
    private ProjectPriority projectPriority; // 프로젝트 우선순위번호

    @OneToOne
    @JoinColumn(nullable = false, name = "projs_no")
    @Enumerated(EnumType.STRING) // Enum을 문자열로 저장
    private ProjectStatus projectStatus; // 프로젝트 상태번호
}
