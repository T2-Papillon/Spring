package com.boogle.papplan.entity;

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
