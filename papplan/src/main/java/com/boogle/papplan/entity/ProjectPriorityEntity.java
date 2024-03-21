package com.boogle.papplan.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ProjectPriorityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "projp_no", length = 20)
    private String projpNo;

    @Column(name = "projp_name")
    private ProjectStatus projpName;

}
