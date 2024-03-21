package com.boogle.papplan.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ProjectPriorityEntity {

    @Id
    @Column(name = "projp_no")
    private String projpNo;

    @Column(name = "projp_name")
    private ProjectStatus projpName;

}
