package com.wafflestudio.draft.model.enums;

public enum RoomStatus {
    WAITING("waiting"),
    PLAYING("playing"),
    FINISHED("finished"),
    CLOSED("closed");

    private String value;

    RoomStatus(String value) {
        this.value = value;
    }

    public String getKey() {
        return name();
    }

    public String getValue() {
        return value;
    }
}
