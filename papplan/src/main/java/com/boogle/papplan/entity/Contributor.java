package com.boogle.papplan.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Contributor{

    /*@EmbeddedId
    private ContributorId id;*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proj_no", referencedColumnName = "proj_no", nullable = false)
    private Project project; // 프로젝트번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eno", referencedColumnName = "eno", nullable = false)
    private Employees employees; // 사번

}
