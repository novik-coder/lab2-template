package com.study.common.dto.library;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BookCondition {
    EXCELLENT,
    GOOD,
    BAD;

    @JsonCreator
    public static BookCondition fromString(String value) {
        return value == null ? null : BookCondition.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String getValue() {
        return this.name();
    }
}
