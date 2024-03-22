package com.boogle.papplan.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity(name = "JobTitle")
@Getter
public class Position {

    @Id
    @Column(length = 20, name = "position_id", nullable = false)
    private String position_id;

    @Column(length = 20, nullable = false)
    private String position_name;
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
