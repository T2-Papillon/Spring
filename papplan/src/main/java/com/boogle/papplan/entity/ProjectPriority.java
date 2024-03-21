package com.boogle.papplan.entity;

import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public enum ProjectPriority {

    TODO("긴급"),
    GOING("높음"),
    DONE("보통"),
    HOLD("낮음");

    private final String value;

    ProjectPriority(String value) {
        this.value = value;
    }
}
