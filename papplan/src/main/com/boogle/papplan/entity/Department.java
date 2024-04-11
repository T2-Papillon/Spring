package com.boogle.papplan.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Department")
@Data
public class Department {

    @Id
    @Column(length = 20, name = "dept_no", nullable = false)
    private String dept_no;

    @Column(length = 20, nullable = false)
    private String dept_name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.REMOVE)
    private List<Employees> employees = new ArrayList<>();

}
