package com.boogle.papplan.entity;

import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public enum ProjectStatus {

    TODO("진행예정"),
    GOING("진행중"),
    DONE("완료"),
    HOLD("보류");

    private final String value;

    ProjectStatus(String value) {
        this.value = value;
    }

}
