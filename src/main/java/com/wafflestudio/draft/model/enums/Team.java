package com.wafflestudio.draft.model.enums;

public enum Team {
    A("a"),
    B("b"),
    NONE("none");

    private String value;

    Team(String value) {
        this.value = value;
    }

    public String getKey() {
        return name();
    }

    public String getValue() {
        return value;
    }
}
