package com.boogle.papplan.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Contributor {

    @ManyToOne
    @JoinColumn(nullable = false, name = "proj_no")
    private Project project; // 프로젝트번호

    @ManyToOne
    @JoinColumn(nullable = false, name = "eno")
    private Employees employees; // 사번

}
