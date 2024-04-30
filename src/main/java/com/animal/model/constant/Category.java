package com.animal.model.constant;

import lombok.Getter;

@Getter
public enum Category {

    FIRST("first"),
    SECOND("second"),
    THIRD("third"),
    FOURTH("fourth");

    private final String label;

    Category(String label) {
        this.label = label;
    }
}