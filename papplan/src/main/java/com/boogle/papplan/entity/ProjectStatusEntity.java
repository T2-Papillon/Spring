package com.boogle.papplan.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ProjectStatusEntity {

    @Id
    @Column(name = "projs_no")
    private String projsNo;

    @Column(name = "projs_name")
    private ProjectStatus projsName;

}
