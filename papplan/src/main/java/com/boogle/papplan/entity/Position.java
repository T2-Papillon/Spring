package com.boogle.papplan.entity;

import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public enum Position {

    TEAM_LEADER("팀장"),
    STAFF("사원");

    private final String value;

    Position(String value) {
        this.value = value;
    }

}