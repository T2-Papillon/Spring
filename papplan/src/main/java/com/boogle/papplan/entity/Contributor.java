package com.boogle.papplan.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*// 별도의 복합 키 클래스 정의
@NoArgsConstructor
@Data
@Embeddable
class ContributorId implements Serializable {
    private Integer proj_no;
    private Integer eno;
}*/

@Data
@Entity
public class Contributor{

    /*@EmbeddedId
    private ContributorId id;*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "proj_no", referencedColumnName = "proj_no", nullable = false)
    private Project project; // 프로젝트번호

    @ManyToOne
    @JoinColumn(name = "eno", referencedColumnName = "eno", nullable = false)
    private Employees employees; // 사번

}
