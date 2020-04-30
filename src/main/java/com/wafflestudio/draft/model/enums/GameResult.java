package com.wafflestudio.draft.model.enums;

public enum GameResult {
    WIN("win"),
    LOSE("lose"),
    DRAW("draw");

    private String value;

    GameResult(String value) {
        this.value = value;
    }

    public String getKey() {
        return name();
    }

    public String getValue() {
        return value;
    }
}
