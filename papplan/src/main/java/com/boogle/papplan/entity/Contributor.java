package com.boogle.papplan.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Contributor {

    @ManyToOne
    @JoinColumn(nullable = false, name = "proj_no")
    private Project project;

    @ManyToOne
    @JoinColumn(nullable = false, name = "eno")
    private Employees employees;

}
