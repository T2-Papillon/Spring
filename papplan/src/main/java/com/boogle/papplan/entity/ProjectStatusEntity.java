package com.boogle.papplan.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ProjectStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "projs_no", length = 20)
    private String projsNo;

    @Column(name = "projs_name")
    private ProjectStatus projsName;

}
