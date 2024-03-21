package com.boogle.papplan.entity;

import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public class ProjectPriorityEntity {

    @Id
    @Column(name = "projp_no")
    private String projpNo;

    @Column(name = "projp_name")
    private ProjectStatus projpName;

}
