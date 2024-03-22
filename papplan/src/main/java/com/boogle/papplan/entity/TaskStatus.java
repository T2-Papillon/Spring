package com.boogle.papplan.entity;

import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public enum TaskStatus {
    TODO("진행예정"),
    DOING("진행중"),
    DONE("완료"),
    HOLD("보류"),
    TEST("테스트");

    private final String value;

    TaskStatus(String value) {
        this.value = value;
    }
}
