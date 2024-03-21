package com.boogle.papplan.Dto;

import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public enum TaskPriority{
    LV0("긴급"),
    LV1("높음"),
    LV2("보통"),
    LV3("낮음");

    private final String value;

    TaskPriority(String value) {
        this.value = value;
    }

}
