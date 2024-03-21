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
    private Integer projNo;

    @Column(length = 50, nullable = false, name = "proj_name")
    private String projName;

    @Column(length = 50, nullable = false, name = "proj_pm")
    private String projPm;

    @Column(length = 20, nullable = false, name = "proj_mem")
    private String projMem;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false, name = "proj_start_date")
    private Date projStartDate;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false, name = "proj_end_date")
    private Date projEndDate;

    @Column(name = "proj_percent")
    private Integer projPercent;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false, name = "proj_create_date")
    private Date projCreateDate;

    @Column(length = 300, nullable = false, name = "proj_desc")
    private String projDesc;

    @OneToOne
    @JoinColumn(nullable = false, name = "projp_no")
    private Project projpNo;

    @OneToOne
    @JoinColumn(nullable = false, name = "projs_no")
    private Project projsNo;

}
