package com.boogle.papplan.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "JobTitle")
@Data
public class Position {

    @Id
    @Column(length = 20, name = "position_id", nullable = false)
    private String position_id;

    @Column(length = 20, nullable = false)
    private String position_name;

    @OneToMany(mappedBy = "position", cascade = CascadeType.REMOVE)
    private List<Employees> employees = new ArrayList<>();
}

/*
enum Position {

    TEAM_LEADER("팀장"),
    STAFF("사원");

    private final String value;

    Position(String value) {
        this.value = value;
    }

}*/
