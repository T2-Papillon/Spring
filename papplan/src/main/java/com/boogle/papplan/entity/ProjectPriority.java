package com.boogle.papplan.entity;

import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public enum ProjectPriority {

    LV0("긴급"),
    LV1("높음"),
    LV2("보통"),
    LV3("낮음");

    private final String value;

    ProjectPriority(String value) {
        this.value = value;
    }

}
