package com.boogle.papplan.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Employees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eno")
    private Integer eno; // 사번

    @Column(length = 50, nullable = false)
    private String email; // 이메일

    @Column(length = 20, nullable = false)
    private String password; // 비밀번호

    @Column(length = 20, nullable = false)
    private String name; // 이름

    @OneToOne
    @JoinColumn(nullable = false, name = "dept_no")
    @Enumerated(EnumType.STRING) // Enum을 문자열로 저장
    private Department department; // 부서번호

    @OneToOne
    @JoinColumn(nullable = false, name = "position_no")
    @Enumerated(EnumType.STRING) // Enum을 문자열로 저장
    private Position position; // 직책번호
}
