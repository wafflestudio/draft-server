package com.wafflestudio.draft.model.enums

enum class RoomStatus(private val value: String) {
    WAITING("waiting"), PLAYING("playing"), FINISHED("finished"), CLOSED("closed");

    fun getKey(): String {
        return name
    }

    fun getValue(): String {
        return value
    }

}