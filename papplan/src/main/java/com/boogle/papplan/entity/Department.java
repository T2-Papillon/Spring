package com.boogle.papplan.entity;

import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public enum Department {

    PL("기획팀"),
    DS("디자인팀"),
    DEV1("개발1팀"),
    DEV2("개발2팀"),
    MKT1("마케팅1팀"),
    MKT2("마케팅2팀"),
    HR("인사팀"),
    FIN("재무팀"),
    SLS("영업팀"),
    CS("CS팀"),
    OS("해외영업팀"),
    SYS("시스템개발팀");

    private final String value;

    Department(String value) {
        this.value = value;
    }

}
