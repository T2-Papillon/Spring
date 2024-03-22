package com.boogle.papplan.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "Department")
@Getter
public class Department {

    @Id
    @Column(length = 20, name = "dept_no", nullable = false)
    private String dept_no;

    @Column(length = 20, nullable = false)
    private String dept_name;

}
